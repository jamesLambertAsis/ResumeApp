package com.example.jla.presentation.screens.my_apps.chat.composable


import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jla.R
import com.example.jla.domain.model.Chat
import com.example.jla.ui.theme.ErrorRed
import com.example.jla.ui.theme.PaleSkyBlue
import com.example.jla.ui.theme.ShadeBlue

@Composable
fun ChatItem(modifier: Modifier, alignment: Alignment.Horizontal, chat: Chat, isOwnChat: Boolean) {

    val bgColor =
    if (isOwnChat && chat.sent?.not() == true){
        Color(ErrorRed.value)
    }else if (isOwnChat && chat.sent?.not() == false){
        Color(ShadeBlue.value)
    }else if (isOwnChat && chat.sent?.not() == null){
        Color(ShadeBlue.value)
    }else {
        Color.Gray
    }

    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = alignment
    ) {
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            if (isOwnChat.not()) {
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = "${chat.userName} | ",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light
                )
            }
            Text(
                text = chat.getTimeSentDisplay(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Light
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth(.8f)
                .align(alignment),
            horizontalArrangement = if (isOwnChat) Arrangement.End else Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isOwnChat.not()) {
                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(Color(PaleSkyBlue.value), shape = CircleShape)
                        .size(30.dp)
                ) {
                    Text(
                        text = "${chat.userName.first().uppercase()}${
                            chat.userName.last().uppercase()
                        }",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center),
                        color = if (isSystemInDarkTheme()) Color.Black else Color.White
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            if (isOwnChat) {
                Spacer(modifier = Modifier.width(50.dp))
            }
            if (chat.sending) {
                CircularProgressIndicator(modifier = Modifier.size(10.dp), strokeWidth = 2.dp, color = ShadeBlue)
                Spacer(Modifier.width(6.dp))
            }
            if (chat.sending.not() && chat.sent?.not() == true) {
                Icon(
                    painter = painterResource(R.drawable.ic_error),
                    tint = Color(ErrorRed.value),
                    modifier = Modifier.size(16.dp),
                    contentDescription = "Not sent"
                )
                Spacer(Modifier.width(6.dp))
            }
            Text(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                    .background(bgColor)
                    .padding(8.dp)
                    .wrapContentWidth(),
                text = chat.chat,
                color = if (isSystemInDarkTheme()) Color.Black else Color.White
            )
            if (isOwnChat.not()) {
                Spacer(modifier = Modifier.width(50.dp))
            }
        }
    }
}