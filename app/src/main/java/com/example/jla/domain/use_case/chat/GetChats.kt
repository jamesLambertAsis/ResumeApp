package com.example.jla.domain.use_case.chat

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.jla.core.TaskResult
import com.example.jla.domain.model.Chat
import com.example.jla.domain.repository.ChatRepository
import com.example.jla.presentation.screens.my_apps.chat.ChatViewModel
import kotlinx.coroutines.flow.Flow

class GetChats (
    private val chatRepository: ChatRepository
) {
    operator fun invoke(hasConnection: Boolean): Flow<TaskResult<List<Chat>>> {
        return chatRepository.getChats(hasConnection)
    }

}