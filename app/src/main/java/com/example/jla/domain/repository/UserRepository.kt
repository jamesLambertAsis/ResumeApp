package com.example.jla.domain.repository

import com.example.jla.domain.model.User

interface UserRepository {

    suspend fun addUser(user: User)

    suspend fun signUp(user: User): String

    suspend fun logIn(user: User): Boolean

}