package com.example.jla.presentation.screens.my_apps

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jla.domain.model.User
import com.example.jla.domain.use_case.user.UserUseCase
import com.example.jla.presentation.screens.my_apps.utils.LogInResult
import androidx.compose.runtime.State
import kotlinx.coroutines.launch

class MyAppsViewModel(
    private val userUseCase: UserUseCase
): ViewModel() {

    private val _verifyMsg = mutableStateOf("")
    val verifyMsg = _verifyMsg

    private val _state = mutableStateOf(MyAppsState())
    val state: State<MyAppsState> = _state

    fun resetVerifyMsg() {
        _verifyMsg.value = ""
    }

    private fun addUser(user: User) {
        viewModelScope.launch {
            userUseCase.addUser(user)
        }

    }

    fun signUp(newUser: User) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )
            _verifyMsg.value = userUseCase.signUp(newUser)
            if (_verifyMsg.value == LogInResult.SUCCESS) {
                addUser(newUser)
            }
            _state.value = _state.value.copy(
                isLoading = false
            )
        }
    }

    fun logIn(user: User) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )
            val logInSuccess = userUseCase.logIn(user)
            _verifyMsg.value = if (logInSuccess) LogInResult.SUCCESS else LogInResult.USER_NOT_EXISTS
            _state.value = _state.value.copy(
                isLoading = false
            )
        }
    }

}