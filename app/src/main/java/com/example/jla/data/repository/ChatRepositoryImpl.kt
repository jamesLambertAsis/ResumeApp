package com.example.jla.data.repository

import com.example.jla.core.TaskResult
import com.example.jla.data.remote.model.ApiResponse
import com.example.jla.domain.model.Chat
import com.example.jla.domain.repository.ChatRepository
import com.example.jla.presentation.utils.NetworkMonitor

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class ChatRepositoryImpl(
    db: Firebase
) : ChatRepository {

    private val firestoreDb = db.firestore
    private val chatDocs = firestoreDb.collection("Chats")

    override fun getChats(hasConnection: Boolean): Flow<TaskResult<List<Chat>>> = callbackFlow {

        trySend(TaskResult.Loading)
        val listener = chatDocs
            .orderBy("timeSent")
            .addSnapshotListener { querySnapshot, error ->

                if (error != null || querySnapshot?.isEmpty == true || querySnapshot == null) {
                    trySend(TaskResult.Error(errorMessage = error?.message ?: "Unknown error"))
                    close(error)
                    return@addSnapshotListener
                }
                if (hasConnection) {
                    val batch = firestoreDb.batch()
                    querySnapshot.documents.forEach { doc ->
                        val sent = doc.getBoolean("sent") ?: false
                        val sending = doc.getBoolean("sending") ?: true

                        if (!sending && !sent) {
                            batch.update(doc.reference, mapOf("sending" to false, "sent" to true))
                        }
                    }
                    batch.commit() // commit all updates at once
                }
                val chatList = querySnapshot.toObjects(Chat::class.java)
                trySend(TaskResult.Success(chatList))
            }

        awaitClose {
            listener.remove()
        }
    }

    override fun resendFailedChats(): Flow<TaskResult<List<Chat>>> = callbackFlow{

    }

    override suspend fun sendChat(message: String): ApiResponse<Boolean, Exception> {
        val docRef = chatDocs.document()
        val chat = Chat(id = docRef.id, chat = message, sending = true)
        return try {
            withTimeout(30_000) {
                docRef.set(chat).await()
                docRef.update(
                    mapOf(
                        "sending" to false,
                        "sent" to true
                    )
                ).await()
                ApiResponse.Success(data = true)
            }
        } catch (e: TimeoutCancellationException) {
            // Timed out after 30 seconds
            docRef.update(
                mapOf(
                    "sending" to false,
                    "sent" to false
                )
            )
            return ApiResponse.Error(e, e.message)

        } catch (e: Exception) {
            docRef.update(
                mapOf(
                    "sending" to false,
                    "sent" to false
                )
            )
            return ApiResponse.Error(e, e.message)
        }
    }

    override suspend fun updateChat(
        docRef: String,
        message: String
    ): ApiResponse<Boolean, Exception> {

        val docRef = chatDocs.document(docRef)

        return try {
            withTimeout(30_000) {
                docRef.update(
                    mapOf(
                        "chat" to message,
                        "edited" to true
                    )
                ).await()
                ApiResponse.Success(true)
            }
        } catch (e: TimeoutCancellationException) {
            ApiResponse.Error(e, errorMessage = e.message)

        } catch (e: Exception) {
            ApiResponse.Error(e, e.message)
        }
    }


}