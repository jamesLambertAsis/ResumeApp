package com.example.jla.presentation.screens.my_apps

sealed class MyAppsState {
    object Loading: MyAppsState()
    object LogInSuccess: MyAppsState()
    object SignUpSuccess: MyAppsState()
    data class Error(val error: String): MyAppsState()
    object Idle: MyAppsState()
}
