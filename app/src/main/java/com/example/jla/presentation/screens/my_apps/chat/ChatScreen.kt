package com.example.jla.presentation.screens.my_apps.chat

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jla.R
import com.example.jla.presentation.screens.my_apps.chat.composable.ChatContent
import com.example.jla.presentation.screens.my_apps.composable.CustomLoadingDialog
import com.example.jla.presentation.screens.my_apps.utils.ChatUtils
import com.example.jla.presentation.screens.my_apps.utils.ShowToast
import com.example.jla.presentation.utils.NetworkMonitor
import com.example.jla.ui.theme.ErrorRed
import org.koin.androidx.compose.koinViewModel


@Composable
fun Chat(
    chatViewModel: ChatViewModel = koinViewModel(),
    back: () -> Unit
) {

    val context = LocalContext.current

    val state = chatViewModel.state.collectAsState().value

    val message = remember {
        mutableStateOf("")
    }
    val chatList by chatViewModel.chats.collectAsState()

    val scrollState = rememberLazyListState()

    val isOnline by NetworkMonitor(context).isOnline.collectAsState(initial = true)

    LaunchedEffect(chatList.size) {
        if (chatList.isNotEmpty()) scrollState.scrollToItem(chatList.size - 1)
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
                Modifier.fillMaxWidth().padding(vertical = 4.dp).background(Color(ErrorRed.value)),
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
            chatList.forEach { chat ->
                item {
                    ChatContent(chat)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
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
            Spacer(modifier = Modifier.width(14.dp))
            if (message.value.trim().isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .weight(.3f)
                        .padding(end = 14.dp)
                        .size(30.dp)
                        .clickable {
                            chatViewModel.onEvent(
                                ChatEvent.SendChat(message = message.value.trim())
                            )
//                            message.value = "POyZGbTZMV0ze"
                            message.value = ""
                        }
                        .scale(-1f),
                    painter = painterResource(R.drawable.ic_arrow_back),
//                    contentDescription = "asisjms72@gmail.com",
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }
        when(state) {
            is ChatUiState.Loading -> {
                CustomLoadingDialog()
            }

            is ChatUiState.Error -> {
                ShowToast(state.error)
            }
            ChatUiState.Idle -> Unit
            ChatUiState.SuccessFetch -> Unit
            ChatUiState.SuccessSend -> Unit
            else -> Unit
        }
    }

}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}