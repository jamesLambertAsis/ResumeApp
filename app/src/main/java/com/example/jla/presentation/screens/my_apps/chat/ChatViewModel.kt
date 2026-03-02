package com.example.jla.presentation.screens.my_apps.chat

import com.example.jla.core.BaseViewModel
import com.example.jla.core.TaskResult
import com.example.jla.domain.model.Chat
import com.example.jla.domain.use_case.chat.ChatUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class ChatViewModel(
    private val chatUseCase: ChatUseCase
): BaseViewModel<ChatUiState>(ChatUiState.Idle) {


    init {
        run {
            chatUseCase.getChats().distinctUntilChanged().collect { result ->
                when (result) {
                    is TaskResult.Error<*> -> {
                        setState(ChatUiState.Error(result.errorMessage))
                    }
                    TaskResult.Loading -> {
                        setState(ChatUiState.Loading)
                    }
                    is TaskResult.Success<List<Chat>> -> {
                        setState(ChatUiState.SuccessFetchChat(result.data))
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
                        setState(ChatUiState.Error(result.errorMessage))
                    }
                    TaskResult.Loading -> {
                        setState(ChatUiState.Sending)
                    }
                    is TaskResult.Success<*> -> {
                        setState(ChatUiState.SuccessSend)
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