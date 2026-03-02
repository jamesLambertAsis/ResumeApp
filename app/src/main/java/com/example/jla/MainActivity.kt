package com.example.jla

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.jla.presentation.navigation.nav.navGraphApp
import com.example.jla.presentation.navigation.nav_items.AppNavigationItem
import com.example.jla.ui.theme.JLATheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {

            JLATheme {
                Scaffold {
                    val navController = rememberNavController()
                    NavHost(
                        modifier = Modifier
                            .imePadding()
                            .navigationBarsPadding()
                            .statusBarsPadding(),
                        navController = navController,
                        startDestination = AppNavigationItem.HomeScreen
                    ) {
                        navGraphApp(navController)
                    }
                }

            }
        }
    }
}



