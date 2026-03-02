package com.example.jla.domain.repository

import com.example.jla.domain.model.User
import com.example.jla.presentation.screens.my_apps.utils.SignUpResult

interface UserRepository {

    suspend fun addUser(user: User)

    suspend fun signUp(user: User): SignUpResult

    suspend fun logIn(user: User): Boolean

}