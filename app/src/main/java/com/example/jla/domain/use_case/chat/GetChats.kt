package com.example.jla.domain.use_case.chat

import com.example.jla.domain.model.Chat
import com.example.jla.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetChats (
    private val chatRepository: ChatRepository
) {

    suspend operator fun invoke(): Flow<List<Chat>> {
        return chatRepository.getChats()
    }

}