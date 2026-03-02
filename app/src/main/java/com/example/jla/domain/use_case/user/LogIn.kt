package com.example.jla.domain.use_case.user

import android.util.Log
import com.example.jla.core.TaskResult
import com.example.jla.domain.model.User
import com.example.jla.domain.repository.UserRepository
import com.example.jla.presentation.screens.my_apps.utils.LogInResult
import kotlinx.coroutines.flow.Flow

class LogIn (
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): String {
        val result = repository.logIn(user)
        Log.d("xxx-->", "invoke: "+result)
        return if (result) LogInResult.SUCCESS else LogInResult.USER_NOT_EXISTS
    }
}