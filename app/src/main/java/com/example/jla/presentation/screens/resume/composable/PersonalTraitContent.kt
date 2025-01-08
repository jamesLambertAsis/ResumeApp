package com.example.jla.presentation.screens.resume.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PersonalTraitContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        BulletedItem(item = "Fast Learner")
        BulletedItem(item = "Willing to Learn")
        BulletedItem(item = "Problem Solving")
        BulletedItem(item = "Communication")
    }
}