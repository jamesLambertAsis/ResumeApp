package com.example.jla.presentation.screens.my_apps.chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jla.R
import com.example.jla.domain.model.Chat
import com.example.jla.presentation.screens.my_apps.chat.composable.ChatContent
import com.example.jla.presentation.screens.my_apps.composable.CustomLoadingDialog
import com.example.jla.presentation.screens.my_apps.utils.ChatUtils
import com.example.jla.presentation.screens.my_apps.utils.ShowToast
import com.example.jla.presentation.utils.NetworkMonitor
import com.example.jla.ui.theme.ErrorRed
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel
import kotlin.collections.mutableListOf


@Composable
fun Chat(
    chatViewModel: ChatViewModel = koinViewModel(),
    back: () -> Unit
) {

    val context = LocalContext.current

    val state by chatViewModel.state.collectAsStateWithLifecycle()
    val uiState = state
    val message = remember {
        mutableStateOf("")
    }

    var messages by remember {
        mutableStateOf(listOf<Chat>())
    }

    val scrollState = rememberLazyListState()

    val isOnline by NetworkMonitor(context).isOnline.collectAsStateWithLifecycle(initialValue = true)

    LaunchedEffect(messages) {
        if (messages.isNotEmpty()) scrollState.scrollToItem(messages.size - 1)
    }

    LaunchedEffect(isOnline) {
        chatViewModel.onEvent(ChatEvent.UpdateHasConnection(isOnline))
    }

    Column(
        modifier = Modifier
            .imePadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.3f)
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickable(
                        interactionSource = null,
                        indication = null
                    ) {
                        back()
                    }
                    .align(Alignment.CenterStart)
                    .padding(start = 14.dp),
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = null
            )
            Text(
                text = ChatUtils.loggedInUserName,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                fontSize = 26.sp
            )
        }
        if (isOnline.not()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .background(Color(ErrorRed.value)),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("No Connection", fontSize = 14.sp)
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = scrollState,
            reverseLayout = false
        ) {
            items(items = messages, key = {chat -> chat.id}) { chat ->
                ChatContent(chat)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(3f),
                value = message.value,
                onValueChange = {
                    message.value = it
                },
                placeholder = { Text(text = "Type a message...") },
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray
                ),
                maxLines = 3
            )
            if (message.value.trim().isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .weight(.4f)
                        .padding(end = 14.dp)
                        .size(34.dp)
                        .clickable {
                            chatViewModel.onEvent(
                                ChatEvent.SendChat(message = message.value.trim())
                            )
                            message.value = ""
                        },
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }
        LaunchedEffect(uiState) {
            Log.d("xxx-->", "Chat: "+uiState)
        }
        when(uiState) {
            is ChatUiState.Loading -> {
                CustomLoadingDialog()
            }

            is ChatUiState.Error -> {
                ShowToast(uiState.error)
            }
            ChatUiState.Idle -> Unit
            is ChatUiState.SuccessFetchChat -> {
                messages = uiState.chats
            }
            else -> Unit
        }
    }

}