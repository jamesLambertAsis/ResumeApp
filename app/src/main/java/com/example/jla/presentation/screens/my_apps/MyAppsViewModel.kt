package com.example.jla.presentation.screens.my_apps

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jla.domain.model.User
import com.example.jla.domain.use_case.user.UserUseCase
import com.example.jla.presentation.screens.my_apps.utils.LogInResult
import androidx.compose.runtime.State
import com.example.jla.core.BaseViewModel
import com.example.jla.presentation.screens.my_apps.utils.ChatUtils
import kotlinx.coroutines.launch

class MyAppsViewModel(
    private val userUseCase: UserUseCase
): BaseViewModel<MyAppsState>(MyAppsState.Idle) {

    fun onEvent(event: MyAppsEvent) {
        when (event) {
            is MyAppsEvent.LogIn -> {
                runOnBackground {
                    _state.value = MyAppsState.Loading
                    val result = userUseCase.logIn(User(event.mobileNum, event.userName))
                    run {
                        if (result == LogInResult.SUCCESS) {
                            _state.value = MyAppsState.LogInSuccess
                            ChatUtils.loggedInUserName = event.userName
                        } else {
                            _state.value =
                                MyAppsState.Error(result)
                        }
                    }
                }
            }

            is MyAppsEvent.SignUp -> {
                run {
                    _state.value = MyAppsState.Loading
                    val result = userUseCase.signUp(User(event.mobileNum, event.userName))
                    if (result == LogInResult.SUCCESS) _state.value = MyAppsState.SignUpSuccess else _state.value =
                        MyAppsState.Error(result)
                }
            }
        }
    }
}