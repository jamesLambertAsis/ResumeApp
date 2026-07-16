package com.example.jla.presentation.screens.skill_sets.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jla.presentation.utils.ThemeUtils
import com.example.jla.ui.theme.ShadeBlue

@Composable
fun BlackLightBulb(height: Dp, width: Dp, isUpsideDown: Boolean, size: Dp) {
    Row{
        if (isUpsideDown){

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier
                    .height(height)
                    .width(width)
                    .background(ShadeBlue))
                Box(modifier = Modifier
                    .offset(y = (-4).dp)
                    .background(ShadeBlue, CircleShape)
                    .size(size),
                ){
                    Box(modifier = Modifier
                        .background(color = if (ThemeUtils.isDarkMode()) Color.Black else Color.White, CircleShape)
                        .size(size / 3)
                        .align(Alignment.Center)
                    )
                }
            }

        } else {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier
                    .background(ShadeBlue, CircleShape)
                    .size(size),
                ){
                    Box(modifier = Modifier
                        .background(color = if (ThemeUtils.isDarkMode()) Color.Black else Color.White, CircleShape)
                        .size(size / 3)
                        .align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier
                    .height(height)
                    .width(width)
                    .background(ShadeBlue))
            }
        }
    }
}