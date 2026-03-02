package com.example.jla.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenMeteoResponse(
    val latitude: Float,
    val longitude: Float,
    val timezone: String,
    @SerialName("current_weather")
    val currentWeather: CurrentWeather
)

@Serializable
data class CurrentWeather(
    val temperature: Float,
    val windspeed: Float,
    val weathercode: Int,
    val time: String
)