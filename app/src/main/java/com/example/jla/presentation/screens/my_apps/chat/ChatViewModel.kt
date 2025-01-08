package com.example.jla.presentation.screens.my_apps.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jla.domain.model.Chat
import com.example.jla.domain.repository.ChatRepository
import com.example.jla.domain.use_case.chat.ChatUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatUseCase: ChatUseCase
): ViewModel() {

    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats = _chats

    init {
        viewModelScope.launch {
            chatUseCase.getChats().collect { chats ->
                _chats.value = chats
            }
        }
    }

    fun sendChat(chat: Chat) {
        viewModelScope.launch {
            chatUseCase.sendChat(chat)
        }
    }

}