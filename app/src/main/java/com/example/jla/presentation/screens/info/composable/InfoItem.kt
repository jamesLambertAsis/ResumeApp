package com.example.jla.presentation.screens.info.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoItem(uniCodeIcon: String, label: String, text: String) {

    Column(
        modifier = Modifier.padding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = uniCodeIcon, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = text)
    }

}