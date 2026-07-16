package com.example.jla.presentation.screens.skill_sets.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun RowOfBlackLightBulbs() {

    val bulbs = remember() {
        List(15) {
            BulbData(
                height = Random.nextInt(40, 100).dp,
                width = Random.nextInt(2, 10).dp,
                size = Random.nextInt(10, 30).dp,
            )
        }
    }

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        bulbs.forEach { bulb ->
            BlackLightBulb(
                height = bulb.height,
                width = bulb.width,
                isUpsideDown = true,
                size = bulb.size,
            )
        }
    }
}

data class BulbData(
    val height: Dp,
    val width: Dp,
    val size: Dp
)