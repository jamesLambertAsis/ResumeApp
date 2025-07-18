package com.example.jla.presentation.screens.info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jla.presentation.screens.info.composable.DotsElastic
import com.example.jla.presentation.screens.info.composable.InfoItem

@Composable
fun Info(modifier: Modifier = Modifier, dismissScreen: () -> Unit) {

    Box(modifier = Modifier
        .fillMaxSize()
        .clickable { }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {

              InfoItem(
                  uniCodeIcon = "\uD83D\uDCF1",
                  label = "AppName",
                  text = "James Resume App"
              )

            Row (
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Development Ongoing",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                DotsElastic()
            }
        }
    }
}