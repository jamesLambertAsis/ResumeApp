package com.example.jla.domain.use_case.chat

import com.example.jla.core.TaskResult
import com.example.jla.data.remote.model.ApiResponse
import com.example.jla.domain.model.Chat
import com.example.jla.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SendChat(
    private val chatRepository: ChatRepository
) {

    operator fun invoke(chat: String): Flow<TaskResult<Unit>> = flow {
        emit(TaskResult.Loading)
        when (val response = chatRepository.sendChat(chat)) {
            is ApiResponse.Error<*> -> {
                emit(TaskResult.Error(errorMessage = response.errorMessage ?: "Unknown Error"))
            }
            is ApiResponse.Success<*> -> {
                emit(TaskResult.Success(Unit))
            }
        }
    }
}