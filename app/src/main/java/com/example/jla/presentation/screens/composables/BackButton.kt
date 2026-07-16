package com.example.jla.presentation.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jla.R
import com.example.jla.presentation.utils.ThemeUtils
import com.example.jla.ui.theme.RoyalBlue

@Composable
fun BackButton(
    modifier: Modifier,
    enableBgColor: Boolean = false,
    onBack: () -> Unit,
) {
    val bgColor = if (enableBgColor) {
        if (ThemeUtils.isDarkMode()) Color.Black else Color.White
    } else {
        Color.Transparent
    }
    Box(
        Modifier
            .offset(x = (-30).dp, y = (-30).dp)
            .clip(RoundedCornerShape(20))
            .background(bgColor)
            .border(2.dp, RoyalBlue, RoundedCornerShape(20))
            .clickable {
                onBack()
            }
            .padding(8.dp)
            .then(modifier)
    ) {
        Icon(
            modifier = Modifier
                .size(30.dp),
            tint = RoyalBlue,
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = null
        )
    }
}