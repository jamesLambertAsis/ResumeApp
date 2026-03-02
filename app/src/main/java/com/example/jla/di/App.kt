package com.example.jla.di

import com.example.jla.data.remote.OpenMeteoApi
import com.example.jla.domain.repository.UserRepository
import com.example.jla.data.repository.UserRepositoryImpl
import com.example.jla.data.repository.ChatRepositoryImpl
import com.example.jla.data.repository.MapRepositoryImpl
import com.example.jla.domain.repository.ChatRepository
import com.example.jla.domain.repository.MapRepository
import com.example.jla.domain.use_case.chat.GetChats
import com.example.jla.domain.use_case.chat.SendChat
import com.example.jla.domain.use_case.user.AddUser
import com.example.jla.domain.use_case.user.LogIn
import com.example.jla.domain.use_case.user.UserUseCase
import com.example.jla.domain.use_case.chat.ChatUseCase
import com.example.jla.domain.use_case.map.GetWeatherDetails
import com.example.jla.domain.use_case.map.MapUseCase
import com.example.jla.domain.use_case.user.SignUp
import com.example.jla.presentation.screens.my_apps.chat.ChatViewModel
import com.example.jla.presentation.screens.my_apps.MyAppsViewModel
import com.example.jla.presentation.screens.my_apps.maps.MapViewModel
import com.google.firebase.Firebase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit


@OptIn(ExperimentalSerializationApi::class)
val app = module {

    val json = Json {
        ignoreUnknownKeys = true
    }

    single { Firebase }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(OpenMeteoApi::class.java)
    }

    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::ChatRepositoryImpl).bind<ChatRepository>()
    singleOf(::MapRepositoryImpl).bind<MapRepository>()

    single { AddUser(get()) }
    single { SignUp(get()) }
    single { LogIn(get()) }
    singleOf(::UserUseCase)

    single { GetChats(get()) }
    single { SendChat(get()) }
    singleOf(::ChatUseCase)

    single { GetWeatherDetails(get()) }
    singleOf(::MapUseCase)

    viewModelOf(::MyAppsViewModel)
    viewModelOf(::ChatViewModel)
    viewModelOf(::MapViewModel)

}
