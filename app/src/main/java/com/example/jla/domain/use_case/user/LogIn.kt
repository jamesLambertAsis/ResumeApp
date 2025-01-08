package com.example.jla.domain.use_case.user

import com.example.jla.domain.model.User
import com.example.jla.domain.repository.UserRepository

class LogIn (
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): Boolean {
        return repository.logIn(user)
    }
}