package com.example.jla.presentation.screens.resume.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun WorkExperienceContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = "ViralPicks Inc.",
                color = Color.Black
            )
            Text(
                fontWeight = FontWeight.Bold,
                text = "Aug. 2023-July 2024",
                color = Color.Black
            )
        }
        HorizontalDivider()
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            fontWeight = FontWeight.Bold,
            text = "Software Engineer – Android Developer",
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        BulletedItem(item = "Direct POS")
        BulletedItem(item = "Native App Development using MVI + Clean Architecture")
        Spacer(modifier = Modifier.height(26.dp))
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = "OPSolutions PH",
                color = Color.Black
            )
            Text(
                fontWeight = FontWeight.Bold,
                text = "Aug. 2021-Aug. 2023",
                color = Color.Black
            )
        }
        HorizontalDivider()
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            fontWeight = FontWeight.Bold,
            text = "Software Engineer – Android Developer",
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        BulletedItem(item = "Jardine Distribution Inc - Construction Supply App")
        BulletedItem(item = "Jardine Distribution Inc – Agricultural Products App")
        BulletedItem(item = "Prime BMD – Pioneer developer")
        BulletedItem(item = "Handle APIs on App")
        BulletedItem(item = "Native App Development using MVP")
    }
}