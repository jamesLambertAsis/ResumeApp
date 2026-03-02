package com.example.jla.domain.use_case.user

import android.util.Log
import androidx.compose.ui.graphics.vector.addPathNodes
import com.example.jla.domain.model.User
import com.example.jla.domain.repository.UserRepository
import com.example.jla.presentation.screens.my_apps.utils.LogInResult
import com.example.jla.presentation.screens.my_apps.utils.SignUpResult

class SignUp (
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): String {
        val result = repository.signUp(user)
        Log.d("xxx-->", "invoke: "+result)
        return when(result) {
            SignUpResult.MOBILE_ALREADY_EXIST -> LogInResult.MOBILE_ALREADY_EXIST
            SignUpResult.USER_NAME_ALREADY_EXIST -> LogInResult.USER_NAME_ALREADY_EXIST
            SignUpResult.SUCCESS -> {
                LogInResult.SUCCESS
            }
        }
    }
}