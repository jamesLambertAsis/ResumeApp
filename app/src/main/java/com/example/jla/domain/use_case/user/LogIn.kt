package com.example.jla.domain.use_case.user

import com.example.jla.domain.model.User
import com.example.jla.domain.repository.UserRepository
import com.example.jla.presentation.screens.my_apps.utils.LogInResult

class LogIn (
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): String {
        val result = repository.logIn(user)
        return if (result) LogInResult.SUCCESS else LogInResult.USER_NOT_EXISTS
    }
}