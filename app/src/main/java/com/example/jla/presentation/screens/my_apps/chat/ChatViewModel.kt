package com.example.jla.presentation.screens.my_apps.chat

import com.example.jla.core.BaseViewModel
import com.example.jla.core.TaskResult
import com.example.jla.domain.use_case.chat.ChatUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

class ChatViewModel(
    private val chatUseCase: ChatUseCase
) : BaseViewModel<ChatUiState>(ChatUiState.Idle) {

    var hasConnection = MutableStateFlow(false)

    init {
        run {
            observeChats()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeChats() {
        run {
            hasConnection.flatMapLatest { online ->
                chatUseCase.getChats(online)
            }
            .distinctUntilChanged()
            .collect { result ->
            when (result) {
                is TaskResult.Error<*> -> setState(ChatUiState.Error(result.errorMessage))
                TaskResult.Loading -> setState(ChatUiState.Loading)
                is TaskResult.Success -> setState(ChatUiState.SuccessFetchChat(result.data))
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

                    }
                }
            }
        }
    }

    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.SendChat -> sendChat(event.message)
            is ChatEvent.UpdateHasConnection -> {
                hasConnection.value = event.hasConnection
            }
        }
    }

}