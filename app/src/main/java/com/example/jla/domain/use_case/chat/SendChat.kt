package com.example.jla.domain.use_case.chat

import com.example.jla.domain.model.Chat
import com.example.jla.domain.repository.ChatRepository

class SendChat(
    private val chatRepository: ChatRepository
) {

    suspend operator fun invoke(chat: Chat) {
        chatRepository.sendChat(chat)
    }

}