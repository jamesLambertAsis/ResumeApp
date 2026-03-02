package com.example.jla.domain.repository

import com.example.jla.core.TaskResult
import com.example.jla.data.remote.model.ApiResponse
import com.example.jla.data.remote.model.OpenMeteoResponse
import com.example.jla.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    fun getChats(hasConnection: Boolean): Flow<TaskResult<List<Chat>>>

    fun resendFailedChats(): Flow<TaskResult<List<Chat>>>

    suspend fun sendChat(message: String): ApiResponse<Boolean, Exception>

    suspend fun updateChat(docRef: String, message: String): ApiResponse<Boolean, Exception>

}