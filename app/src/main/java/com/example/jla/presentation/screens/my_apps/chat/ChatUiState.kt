package com.example.jla.presentation.screens.my_apps.chat

import com.example.jla.domain.model.Chat

sealed interface ChatUiState {

    object Loading: ChatUiState
    object Sending: ChatUiState
    object Idle: ChatUiState
    data class SuccessFetchChat(val chats: List<Chat>): ChatUiState
    data class Error(val error: String): ChatUiState
    object SuccessSend: ChatUiState

}