package com.example.jla.presentation.navigation.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.jla.presentation.navigation.nav_items.AppNavigationItem
import com.example.jla.presentation.screens.home.HomeScreen
import com.example.jla.presentation.screens.info.Info
import com.example.jla.presentation.screens.my_apps.MyApps
import com.example.jla.presentation.screens.my_apps.chat.Chat
import com.example.jla.presentation.screens.my_apps.maps.MapScreen
import com.example.jla.presentation.screens.my_apps.web_view.WebView
import com.example.jla.presentation.screens.resume.ResumeScreen
import com.example.jla.presentation.screens.skill_sets.SkillSetScreen
import com.example.jla.presentation.screens.splash_screen.SplashScreen
import com.example.jla.presentation.utils.HomeOptions

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.navGraphApp(navController: NavHostController) {


    composable<AppNavigationItem.HomeScreen> {

        HomeScreen(
            navController
        ) {
            navController.navigate(
                when (it) {
                    HomeOptions.ABOUT_ME -> AppNavigationItem.ResumeScreen
                    HomeOptions.SKILLS_TOOLS -> AppNavigationItem.SkillSetsScreen
                    HomeOptions.PROJECTS -> AppNavigationItem.MyAppsScreen
                    else -> {
                        AppNavigationItem.InfoScreen
                    }
                }
            )
        }
    }
    composable<AppNavigationItem.SkillSetsScreen> {
        SkillSetScreen()
    }
    composable<AppNavigationItem.ResumeScreen> {
        ResumeScreen {
            navController.navigate(AppNavigationItem.HomeScreen)
        }
    }
    composable<AppNavigationItem.SplashScreen> {
        SplashScreen()
    }
    composable<AppNavigationItem.MyAppsScreen> {
        MyApps(
            toChatScreen = { navController.navigate(AppNavigationItem.ChatScreen) },
            toWebViewScreen = { navController.navigate(AppNavigationItem.WebViewScreen) },
            toMapScreen = { navController.navigate(AppNavigationItem.MapScreen) }
        ) {
            navController.popBackStack()
        }
    }
    composable<AppNavigationItem.InfoScreen> {
        Info {
            navController.popBackStack()
        }
    }
    composable<AppNavigationItem.ChatScreen> {
        Chat {
            navController.popBackStack()
        }
    }
    composable<AppNavigationItem.WebViewScreen> {
        WebView(){
            navController.popBackStack()
        }
    }
    composable<AppNavigationItem.MapScreen> {
        MapScreen()
    }

}