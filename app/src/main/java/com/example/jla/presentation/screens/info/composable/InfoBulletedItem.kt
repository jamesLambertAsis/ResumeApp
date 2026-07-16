package com.example.jla.presentation.screens.info.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jla.presentation.utils.ThemeUtils

@Composable
internal fun InfoBulletedItem(
    item: String,
    version: String = ""
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .clip(shape = CircleShape)
                .background(if (ThemeUtils.isDarkMode()) Color.White else Color.Black)
                .size(6.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = item, fontSize = 18.sp)
        Spacer(modifier = Modifier.width(4.dp))
        if (version.isNotEmpty()) {
            Text(
                text = "($version)",
                fontSize = 12.sp, color = Color.Gray,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ),
                    baselineShift = androidx.compose.ui.text.style.BaselineShift(-0.1f)
                )
            )
        }
    }
}