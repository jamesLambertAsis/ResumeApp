package com.example.jla.presentation.screens.my_apps.chat

sealed class ChatEvent {

    data class SendChat(val message: String): ChatEvent()

}