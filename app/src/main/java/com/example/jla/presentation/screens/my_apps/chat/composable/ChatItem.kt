package com.example.jla.presentation.screens.my_apps.chat.composable

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jla.domain.model.Chat
import com.example.jla.ui.theme.PaleSkyBlue
import com.example.jla.ui.theme.ShadeBlue

@Composable
fun ChatItem(modifier: Modifier, alignment: Alignment.Horizontal, chat: Chat, isOwnChat: Boolean) {

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
            horizontalArrangement = if (isOwnChat) Arrangement.End else Arrangement.Start
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
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            if (isOwnChat) {
                Spacer(modifier = Modifier.width(50.dp))
            }
            Text(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                    .background(
                        if (isOwnChat) Color(ShadeBlue.value) else Color.Gray
                    )
                    .padding(8.dp)
                    .wrapContentWidth(),
                text = chat.chat,
            )
            if (isOwnChat.not()) {
                Spacer(modifier = Modifier.width(50.dp))
            }
        }
    }
}