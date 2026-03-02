package com.example.jla.data.remote

import com.example.jla.data.remote.model.OpenMeteoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi {
    @GET("/v1/forecast")
    suspend fun getCurrentWeather (
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
        @Query("current_weather") currentWeather: Boolean = true,
    ): OpenMeteoResponse
}