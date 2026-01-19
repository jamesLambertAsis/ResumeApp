package com.example.jla.presentation.screens.my_apps.composable


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.jla.ui.theme.RoyalBlue

@Composable
fun CustomLoadingDialog() {

    Dialog(onDismissRequest = {}) {
        Box(
            Modifier.clip(RoundedCornerShape(10.dp)).background(Color.White).padding(30.dp)
        ) {
            CircularProgressIndicator(color = RoyalBlue)
        }
    }

}