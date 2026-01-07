package com.example.jla.presentation.screens.my_apps.chat

import com.example.jla.domain.model.Chat

sealed class ChatUiState {

    object Loading: ChatUiState()
    object Sending: ChatUiState()
    object Idle: ChatUiState()
    object SuccessFetch: ChatUiState()
    data class Error(val error: String): ChatUiState()
    object SuccessSend: ChatUiState()

}