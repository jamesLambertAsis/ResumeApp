package com.example.jla.presentation.screens.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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

@Composable
fun BottomRowItem(
    text: String,
    isSelected: Boolean,
    clicked: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .height(180.dp)
            .width(150.dp)
            .clip(shape = RoundedCornerShape(30.dp, 30.dp, 30.dp, 30.dp))
            .background(Color(0xFF87BFFF))
            .clickable { clicked(text) }
            .border(
                if (isSelected) 2.dp else 0.dp,
                if (isSelected) Color(0xFF3F8EFC) else Color.Transparent,
                shape = RoundedCornerShape(30.dp, 30.dp, 30.dp, 30.dp)
            )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = if (isSelected) 24.sp else 20.sp,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}