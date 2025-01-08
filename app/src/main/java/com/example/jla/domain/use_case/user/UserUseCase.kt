package com.example.jla.domain.use_case.user

data class UserUseCase (
    val addUser: AddUser,
    val signUp: SignUp,
    val logIn: LogIn
)