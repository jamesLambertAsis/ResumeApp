package com.example.jla.domain.use_case.chat

import com.example.jla.core.TaskResult
import com.example.jla.domain.model.Chat
import com.example.jla.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class SendChat(
    private val chatRepository: ChatRepository
) {

    operator fun invoke(chat: String): Flow<TaskResult<Unit>> {
        return chatRepository.sendChat(chat)
    }

}