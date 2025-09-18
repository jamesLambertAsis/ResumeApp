package com.example.jla.presentation.screens.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jla.R
import com.example.jla.presentation.utils.HomeOptions
import com.example.jla.ui.theme.BabyBlue

@Composable
fun BottomRowItem(
    text: String,
    clicked: (String) -> Unit
) {
    val icon = when (text) {
        HomeOptions.MY_RESUME -> R.drawable.ic_document
        HomeOptions.SKILLS_TOOLS -> R.drawable.ic_tools
        HomeOptions.PROJECTS -> R.drawable.ic_application
        else -> R.drawable.ic_info
    }
    Column(
        modifier = Modifier
            .height(180.dp)
            .width(150.dp)
            .clip(shape = RoundedCornerShape(30.dp, 30.dp, 30.dp, 30.dp))
            .background(Color(BabyBlue.value))
            .clickable { clicked(text) }
            .border(
                0.dp,
                Color.Transparent,
                shape = RoundedCornerShape(30.dp, 30.dp, 30.dp, 30.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            Modifier.size(46.dp),
            tint = Color.Black
        )
        Spacer(
            Modifier
                .height(2.dp)
                .width(20.dp)
                .background(Color.Black)
                .padding()
                .padding(top = 16.dp)
        )
        Spacer(Modifier.height(30.dp))
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black
        )
    }
}