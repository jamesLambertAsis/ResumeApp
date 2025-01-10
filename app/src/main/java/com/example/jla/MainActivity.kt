package com.example.jla

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jla.presentation.screens.home.Home
import com.example.jla.presentation.screens.info.Info
import com.example.jla.presentation.screens.my_apps.MyApps
import com.example.jla.presentation.screens.my_apps.chat.Chat
import com.example.jla.presentation.screens.my_apps.utils.ChatUtils
import com.example.jla.presentation.screens.resume.Resume
import com.example.jla.presentation.screens.skill_sets.SkillSets
import com.example.jla.presentation.screens.splash_screen.SplashScreen
import com.example.jla.ui.theme.JLATheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
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
                        startDestination = NavigationItem.HomeScreen
                    ) {
                        composable<NavigationItem.HomeScreen> {
                            Home(
                                navController
                            ){
                                navController.navigate(
                                    when(it) {
                                        "Resume" -> NavigationItem.ResumeScreen
                                        "Skill Sets" -> NavigationItem.SkillSetsScreen
                                        "My Apps" -> NavigationItem.MyApps
                                        else -> { NavigationItem.Info }
                                    }
                                )
                            }
                        }
                        composable<NavigationItem.SkillSetsScreen> {
                            SkillSets(){
                                navController.navigate(NavigationItem.HomeScreen)
                            }
                        }
                        composable<NavigationItem.ResumeScreen> {
                            Resume(){
                                navController.navigate(NavigationItem.HomeScreen)
                            }
                        }
                        composable<NavigationItem.SplashScreen> {
                            SplashScreen()
                        }
                        composable<NavigationItem.MyApps> {
                            MyApps(
                                toChatScreen = { navController.navigate(NavigationItem.ChatScreen) }
                            ){
                                navController.popBackStack()
                            }
                        }
                        composable<NavigationItem.Info> {
                            Info(){
                                navController.popBackStack()
                            }
                        }
                        composable<NavigationItem.ChatScreen> {
                            Chat(){
                                navController.popBackStack()
                            }
                        }
                    }
                }

            }
        }
    }
}

sealed class NavigationItem {

    @Serializable
    object HomeScreen

    @Serializable
    object SkillSetsScreen

    @Serializable
    object ResumeScreen

    @Serializable
    object SplashScreen

    @Serializable
    object MyApps

    @Serializable
    object Info

    @Serializable
    object ChatScreen

}

