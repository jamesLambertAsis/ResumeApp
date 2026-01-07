package com.example.jla.data.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.jla.core.TaskResult
import com.example.jla.domain.model.Chat
import com.example.jla.domain.repository.ChatRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class ChatRepositoryImpl(db: Firebase, private val app: Application): ChatRepository {

    private val firestoreDb = db.firestore
    private val chatDocs = firestoreDb.collection("Chats")

    override fun getChats(): Flow<TaskResult<List<Chat>>> = callbackFlow {

        trySend(TaskResult.Loading)

        val listener = chatDocs
            .orderBy("timeSent")
            .addSnapshotListener { querySnapshot, error ->

                if (error != null || querySnapshot?.isEmpty == true || querySnapshot == null) {
                    trySend(TaskResult.Error(errorMessage = error?.message ?: "Unknown error"))
                    close(error)
                    return@addSnapshotListener
                }
                if (isNetworkAvailable()){
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

    override fun sendChat(message: String): Flow<TaskResult<Unit>> = flow {

        emit(TaskResult.Loading)
        val docRef = chatDocs.document()
        val chat = Chat(id = docRef.id, chat = message, sending = true)

        try {
            withTimeout(30_000) {
                docRef.set(chat).await()
                docRef.update(
                    mapOf(
                        "sending" to false,
                        "sent" to true
                    )
                ).await()
                emit(TaskResult.Success(Unit))
            }
        } catch (e: TimeoutCancellationException) {
            // Timed out after 30 seconds
            docRef.update(
                mapOf(
                    "sending" to false,
                    "sent" to false
                )
            )
            emit(TaskResult.Error(errorMessage = e.message ?:"Request timed out"))

        } catch (e: Exception) {
            docRef.update(
                mapOf(
                    "sending" to false,
                    "sent" to false
                )
            )
            emit(TaskResult.Error(errorMessage = e.message ?: "Unknown error"))
        }
    }

    override fun updateChat(docRef: String, message: String): Flow<TaskResult<Unit>> = flow {
        emit(TaskResult.Loading)
        val docRef = chatDocs.document(docRef)
        try {
            withTimeout(30_000) {
                docRef.update(
                    mapOf(
                        "chat" to message,
                        "edited" to true
                    )
                ).await()
                emit(TaskResult.Success(Unit))
            }

        } catch (e: TimeoutCancellationException) {
            emit(TaskResult.Error(errorMessage = e.message ?:"Request timed out"))

        } catch (e: Exception) {
            emit(TaskResult.Error(errorMessage = e.message ?: "Unknown error"))
        }
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}