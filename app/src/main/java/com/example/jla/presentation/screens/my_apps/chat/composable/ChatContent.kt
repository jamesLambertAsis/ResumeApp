package com.example.jla.presentation.screens.my_apps.chat.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.jla.domain.model.Chat
import com.example.jla.presentation.screens.my_apps.utils.ChatUtils

@Composable
fun ChatContent(chat: Chat) {
    Column(
        Modifier.fillMaxWidth(.9f)
    ) {
        if (chat.userName == ChatUtils.loggedInUserName) {
            ChatItem(
                modifier = Modifier.fillMaxWidth(),
                alignment = Alignment.End,
                chat = chat, isOwnChat =
                chat.userName == ChatUtils.loggedInUserName
            )
        } else {
            ChatItem(
                modifier = Modifier.fillMaxWidth(),
                alignment = Alignment.Start,
                chat = chat,
                isOwnChat = chat.userName == ChatUtils.loggedInUserName
            )
        }
    }
}