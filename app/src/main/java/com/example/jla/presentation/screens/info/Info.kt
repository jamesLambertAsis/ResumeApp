package com.example.jla.presentation.screens.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jla.R
import com.example.jla.presentation.screens.composables.BackButton
import com.example.jla.presentation.screens.info.composable.InfoBulletedItem
import com.example.jla.presentation.utils.ThemeUtils
import com.example.jla.ui.theme.RoyalBlue

@Composable
fun Info(dismissScreen: () -> Unit) {

    val context = LocalContext.current
    val minSdk = context.applicationInfo.minSdkVersion
    val targetSdk = context.applicationInfo.targetSdkVersion
    val compileSdk = context.applicationInfo.compileSdkVersion
    Box(
        Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_code_xml_24),
                    contentDescription = null,
                    tint = RoyalBlue,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20))
                        .size(40.dp)
                        .background(if (ThemeUtils.isDarkMode()) Color.White else Color(0xFF1A1919))
                        .padding(4.dp)
                )
                Spacer(Modifier.width(15.dp))
                Column() {
                    Text("Resumè App", fontSize = 24.sp)
                    Text("App Info", fontSize = 14.sp, color = Color.Gray)
                }
            }
            Spacer(Modifier.height(20.dp))
            Text("About Resumè App ...", fontSize = 24.sp)
            Text(
                "Resumè App is a modern Native Android application built with Jetpack Compose" +
                        "and follows best practices for scalable and maintainable code base",
                fontSize = 14.sp
            )
            Spacer(Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_account_tree_24),
                    contentDescription = null,
                    tint = RoyalBlue,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20))
                        .size(40.dp)
                        .padding(4.dp)
                )
                Spacer(Modifier.width(15.dp))
                Text("Architecture", fontSize = 24.sp)

            }
            Row(
                Modifier.padding(start = 35.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoBulletedItem("MVI + Clean Architecture")
            }
            Text(
                modifier = Modifier.padding(start = 40.dp),
                text = "Separation of concerns with multi-layered structure and undirectional data flow",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_code_xml_24),
                    contentDescription = null,
                    tint = RoyalBlue,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20))
                        .size(40.dp)
                        .padding(4.dp)
                )
                Spacer(Modifier.width(15.dp))
                Text("Tech Stack", fontSize = 24.sp)
            }
            Column(
                Modifier.padding(start = 40.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                InfoBulletedItem(item = "Kotlin", version = "2.3.0")
                InfoBulletedItem(item = "Jetpack Compose", version = "2025.12.01")
                InfoBulletedItem(item = "Kotlin Coroutines and Flow")
                InfoBulletedItem(item = "Koin Dependency Injection", version = "4.1.1")
                InfoBulletedItem(item = "Retrofit", version = "3.0.0")
                InfoBulletedItem(item = "Firebase - Firestore", version = "25.1.4")
                InfoBulletedItem(item = "Gemini AI", version = "0.9.0")
                InfoBulletedItem(item = "Google Maps", version = "4.4.1")
            }
            Spacer(Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_info),
                    contentDescription = null,
                    tint = RoyalBlue,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20))
                        .size(40.dp)
                        .padding(4.dp)
                )
                Spacer(Modifier.width(15.dp))
                Text("Others", fontSize = 24.sp)
            }
            Column(
                Modifier.padding(start = 40.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoBulletedItem(item = "Minimum SDK: $minSdk")
                InfoBulletedItem(item = "Target SDK: $targetSdk")
                InfoBulletedItem(item = "Compile SDK: $compileSdk")
                InfoBulletedItem(item = "Kotlin DSL")
            }
        }

        BackButton(
            Modifier.align(Alignment.BottomEnd)
        ) {
            dismissScreen()
        }
    }


}