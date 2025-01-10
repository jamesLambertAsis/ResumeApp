package com.example.jla.di

import com.example.jla.domain.repository.UserRepository
import com.example.jla.data.repository.UserRepositoryImpl
import com.example.jla.data.repository.ChatRepositoryImpl
import com.example.jla.domain.repository.ChatRepository
import com.example.jla.domain.use_case.chat.GetChats
import com.example.jla.domain.use_case.chat.SendChat
import com.example.jla.domain.use_case.user.AddUser
import com.example.jla.domain.use_case.user.LogIn
import com.example.jla.domain.use_case.user.UserUseCase
import com.example.jla.domain.use_case.chat.ChatUseCase
import com.example.jla.domain.use_case.user.SignUp
import com.example.jla.presentation.screens.my_apps.chat.ChatViewModel
import com.example.jla.presentation.screens.my_apps.MyAppsViewModel
import com.google.firebase.Firebase
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module



val app = module {

    single { Firebase }

    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::ChatRepositoryImpl).bind<ChatRepository>()

    single { AddUser(get()) }
    single { SignUp(get()) }
    single { LogIn(get()) }
    singleOf(::UserUseCase)

    single { GetChats(get()) }
    single { SendChat(get()) }
    singleOf(::ChatUseCase)

    viewModelOf(::MyAppsViewModel)
    viewModelOf(::ChatViewModel)

}
