package com.example.jla.presentation.screens.skill_sets.composable

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jla.R
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
    val rotation by animateFloatAsState(
        targetValue = if (isSelected) 360f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "BoxRotation"
    )
    val caretRotation by animateFloatAsState(
        targetValue = if (isSelected) 90f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "BoxRotation"
    )
    val animatedBoxSize by animateDpAsState(
        targetValue = if (isSelected) 80.dp else 60.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow // Low stiffness makes it move slower
        ),
        label = "BoxSize"
    )
    val animatedIconSize by animateDpAsState(
        targetValue = if (isSelected) 50.dp else 30.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "BoxSize"
    )
    val animatedTextSize by animateFloatAsState(
        targetValue = if (isSelected) 20f else 16f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "BoxSize"
    )
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
                    .size(animatedBoxSize)
                    .graphicsLayer(
                        rotationZ = rotation
                    )
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(animatedIconSize),
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
                fontSize = animatedTextSize.sp
            )

            Icon(
                modifier = Modifier.graphicsLayer(
                    rotationZ = caretRotation
                ),
                painter = painterResource(R.drawable.ic_caret_right),
                contentDescription = null
            )

        }
        AnimatedVisibility(
            visible = isSelected,
            enter = fadeIn(animationSpec = tween(500)) +
                    expandVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ) +
                    slideInVertically(
                        spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ),
            exit = fadeOut(animationSpec = tween(500)) +
                    shrinkVertically() +
                    slideOutVertically(
                        spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
        ) {
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