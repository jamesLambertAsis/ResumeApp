package com.example.jla.domain.repository

import com.example.jla.core.TaskResult
import com.example.jla.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    fun getChats(): Flow<TaskResult<List<Chat>>>

    fun sendChat(message: String): Flow<TaskResult<Unit>>

    fun updateChat(docRef: String, message: String): Flow<TaskResult<Unit>>

}