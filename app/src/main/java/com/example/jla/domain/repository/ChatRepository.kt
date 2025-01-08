package com.example.jla.domain.repository

import com.example.jla.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun getChats(): Flow<List<Chat>>

    suspend fun sendChat(chat: Chat)

}