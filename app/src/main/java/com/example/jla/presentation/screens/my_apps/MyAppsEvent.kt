package com.example.jla.presentation.screens.my_apps

import com.example.jla.domain.model.User

sealed class MyAppsEvent {

    data class LogIn(val userName: String, val mobileNum: String): MyAppsEvent()
    data class SignUp(val userName: String, val mobileNum: String): MyAppsEvent()

}