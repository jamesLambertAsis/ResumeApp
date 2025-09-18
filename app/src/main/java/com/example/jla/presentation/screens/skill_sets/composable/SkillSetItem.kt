package com.example.jla.presentation.screens.skill_sets.composable

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jla.R
import com.example.jla.presentation.screens.resume.composable.BulletedItem
import com.example.jla.presentation.utils.SkillAndroidList
import com.example.jla.presentation.utils.SkillDatabaseList
import com.example.jla.presentation.utils.SkillSet
import com.example.jla.presentation.utils.SkillToolList
import com.example.jla.presentation.utils.SkillWebList
import com.example.jla.presentation.utils.ThemeUtils
import com.example.jla.ui.theme.ShadeBlue

@SuppressLint("UnrememberedMutableInteractionSource", "UnrememberedMutableState")
@Composable
fun SkillSetItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val icon = when (text) {
        SkillSet.ANDROID_DEVELOPMENT -> R.drawable.ic_android
        SkillSet.WEB_DEVELOPMENT -> R.drawable.ic_web_dev
        SkillSet.DATABASE -> R.drawable.ic_database
        else -> R.drawable.ic_tools
    }
    val skillList = when (text) {
        SkillSet.ANDROID_DEVELOPMENT -> SkillAndroidList
        SkillSet.WEB_DEVELOPMENT -> SkillWebList
        SkillSet.DATABASE -> SkillDatabaseList
        else -> SkillToolList
    }
    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(34.dp, 0.dp, 0.dp, 34.dp))
                .clickable {
                    onClick()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(ShadeBlue)
                    .size(if (isSelected) 80.dp else 60.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(if (isSelected) 40.dp else 30.dp),
                    painter = painterResource(icon),
                    tint = if (ThemeUtils.isDarkMode()) Color.Black else Color.White,
                    contentDescription = null
                )
            }
            Spacer(Modifier.width(14.dp))
            Spacer(
                Modifier
                    .width(4.dp)
                    .height(30.dp)
                    .background(ShadeBlue)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = if (isSelected) 20.sp else 16.sp
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )

        }
        AnimatedVisibility(visible = isSelected) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 86.dp)
            ) {
                skillList.forEach { item ->
                    SkillsItem(item)

                }
            }
        }
    }
}

@Composable
fun SkillsItem(item: String) {
    Row(
        Modifier.padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .clip(shape = CircleShape)
                .background(if (ThemeUtils.isDarkMode()) Color.White else Color.Black)
                .size(6.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = item, fontStyle = FontStyle.Italic, fontSize = 20.sp)
    }
}