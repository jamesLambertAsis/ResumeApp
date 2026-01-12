package com.example.jla.presentation.screens.skill_sets

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.jla.R
import com.example.jla.presentation.screens.skill_sets.composable.SkillSetListOption
import com.example.jla.ui.theme.ShadeBlue

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun SkillSetScreen(modifier: Modifier = Modifier) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.5f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ShadeBlue)
                        .fillMaxHeight(.65f)
                )
                Box(
                    modifier = modifier
                        .clip(shape = CircleShape)
                        .background(color = Color.White, shape = CircleShape)
                        .border(4.dp, color = Color(ShadeBlue.value), shape = CircleShape)
                        .align(alignment = Alignment.BottomCenter)
                        .size(180.dp)
                        .zIndex(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_brain),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.Center)
                            .clickable(
                                indication = null,
                                interactionSource = MutableInteractionSource()
                            ) {

                            },
                        tint = Color(ShadeBlue.value)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
            ) {
                Spacer(Modifier.height(20.dp))
                SkillSetListOption()
            }
        }
    }
}