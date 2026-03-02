package com.example.jla.presentation.navigation.nav_items

import kotlinx.serialization.Serializable

sealed class AppNavigationItem {

    @Serializable
    object AppNavGraph

    @Serializable
    object HomeScreen

    @Serializable
    object SkillSetsScreen

    @Serializable
    object ResumeScreen

    @Serializable
    object SplashScreen

    @Serializable
    object MyAppsScreen

    @Serializable
    object InfoScreen

    @Serializable
    object ChatScreen

    @Serializable
    object WebViewScreen

    @Serializable
    object MapScreen

}