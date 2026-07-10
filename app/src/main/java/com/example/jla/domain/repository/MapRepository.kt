package com.example.jla.domain.repository

import com.example.jla.data.remote.model.ApiResponse
import com.example.jla.domain.model.WeatherDetails

interface MapRepository {
    suspend fun getWeatherDetails(latitude: Double, longitude: Double): ApiResponse<WeatherDetails, Exception>

    suspend fun getGenerativeAiResponse(prompt: String): ApiResponse<String, Exception>

}