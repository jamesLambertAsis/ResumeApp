package com.example.jla.data.repository

import com.example.jla.domain.model.Chat
import com.example.jla.domain.repository.ChatRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ChatRepositoryImpl(db: Firebase): ChatRepository {

    private val firestoreDb = db.firestore
    private val chatDocs = firestoreDb.collection("Chats")

    override suspend fun getChats(): Flow<List<Chat>> = callbackFlow {
        val docsListener = chatDocs
            .orderBy("timeSent")
            .addSnapshotListener { querySnapShot, e ->

                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val chatList = querySnapShot?.toObjects(Chat::class.java) ?: emptyList()

                trySend(chatList)
            }

        awaitClose{ docsListener.remove() }
    }

    override suspend fun sendChat(chat: Chat) {
        chatDocs.add(chat).await()
    }


}