package com.example.jla.presentation.screens.skill_sets.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun BlackLightBulb(height: Dp, width: Dp, isUpsideDown: Boolean, size: Dp) {
    Row{
        if (isUpsideDown){

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier
                    .height(height)
                    .width(width)
                    .background(Color.Black))
                Box(modifier = Modifier
                    .background(Color.Black, CircleShape)
                    .size(size),
                ){
                    Box(modifier = Modifier
                        .background(Color.White, CircleShape)
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
                    .background(Color.Black, CircleShape)
                    .size(size),
                ){
                    Box(modifier = Modifier
                        .background(Color.White, CircleShape)
                        .size(size / 3)
                        .align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier
                    .height(height)
                    .width(width)
                    .background(Color.Black))
            }
        }
    }
}