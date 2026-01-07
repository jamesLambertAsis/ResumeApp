package com.example.jla.presentation.screens.my_apps.chat

import com.example.jla.core.BaseViewModel
import com.example.jla.core.TaskResult
import com.example.jla.domain.model.Chat
import com.example.jla.domain.use_case.chat.ChatUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatViewModel(
    private val chatUseCase: ChatUseCase
): BaseViewModel<ChatUiState>(ChatUiState.Idle) {

    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats = _chats.asStateFlow()

    init {
        run {
            chatUseCase.getChats().collect { result ->
                when (result) {
                    is TaskResult.Error<*> -> {
                        _state.value = ChatUiState.Error(result.errorMessage)
                    }
                    TaskResult.Loading -> {
                        _state.value = ChatUiState.Loading
                    }
                    is TaskResult.Success<List<Chat>> -> {
                        _state.value = ChatUiState.SuccessFetch
                        _chats.value = result.data
                    }
                }
            }
        }
    }

    fun sendChat(chat: String) {
        run {
            chatUseCase.sendChat(chat).collect { result ->
                when (result) {
                    is TaskResult.Error<*> -> {
                        _state.value = ChatUiState.Error(result.errorMessage)
                    }
                    TaskResult.Loading -> {
                        _state.value = ChatUiState.Sending
                    }
                    is TaskResult.Success<*> -> {
                        _state.value = ChatUiState.SuccessSend
                    }
                }
            }
        }
    }

    fun onEvent(event: ChatEvent) {
        when(event) {
            is ChatEvent.SendChat -> sendChat(event.message)
        }
    }

}