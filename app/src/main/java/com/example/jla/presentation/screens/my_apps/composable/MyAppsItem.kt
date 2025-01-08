package com.example.jla.presentation.screens.my_apps.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyAppsItem(
    item: String,
    icon: Painter,
    modifier: Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .clickable (
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            ) { onClick() }
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color(0xFF87BFFF))
            .fillMaxHeight(.7f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,

        ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, end = 8.dp, top = 14.dp),
            tint = Color.Black
        )
        Column(
            modifier = Modifier
                .weight(.6f)
                .padding(top = 30.dp)
                .fillMaxWidth()
                .background(Color.Black),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item,
                color = Color(0xFF87BFFF),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}