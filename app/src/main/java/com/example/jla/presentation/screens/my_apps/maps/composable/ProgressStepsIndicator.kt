package com.example.jla.presentation.screens.my_apps.maps.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jla.R
import com.example.jla.ui.theme.RoyalBlue

@Composable
fun ProgressStepsIndicator(
    step: Int,
    msg: String
) {

    Row(
        Modifier.fillMaxWidth()
    ) {
        Column(
            Modifier
                .weight(1f)
                .fillMaxWidth(.95f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (step == 0) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = RoyalBlue)
                Text(
                    msg,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                )
            } else if (step > 0) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = null,
                    tint = Color(0xFF4CAF50)
                )
                Text(
                    "Location Loaded",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                )
            }
        }
        Column(
            Modifier
                .weight(1f)
                .fillMaxWidth(.95f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (step == 1) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = RoyalBlue)
                Text(
                    msg,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                )
            } else if (step > 1) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = null,
                    tint = Color(0xFF4CAF50)
                )
                Text(
                    "Weather Loaded",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                )
            }
        }
        Column(
            Modifier
                .weight(1f)
                .fillMaxWidth(.95f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (step == 2) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = RoyalBlue)
                Text(
                    msg,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                )
            }
        }
    }

}