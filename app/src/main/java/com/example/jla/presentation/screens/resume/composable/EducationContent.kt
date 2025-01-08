package com.example.jla.presentation.screens.resume.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun EducationContent(modifier: Modifier = Modifier) {

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
                text = "Elementary",
                color = Color.Black
            )
            Text(
                fontWeight = FontWeight.Bold,
                text = "2004 - 2010",
                color = Color.Black
            )
        }
        Text(text = "Mahaplag Central School",
            color = Color.Black)

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = "High School",
                color = Color.Black
            )
            Text(
                fontWeight = FontWeight.Bold,
                text = "2010 - 2014",
                color = Color.Black
            )
        }
        Text(text = "Mahaplag Central School",
            color = Color.Black)

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = "College",
                color = Color.Black
            )
            Text(
                fontWeight = FontWeight.Bold,
                text = "2014 - 2015",
                color = Color.Black
            )
        }
        Text(
            text = "Southern Leyte State University\n" +
                    "Bachelor of Industrial Education Major in T.L.E",
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = "College",
                color = Color.Black
            )
            Text(
                fontWeight = FontWeight.Bold,
                text = "2015 - 2021",
                color = Color.Black
            )
        }
        Text(
            text = "Adventist University of the Philippines\n" +
                    "BS in Information Technology",
            color = Color.Black
        )
    }
}