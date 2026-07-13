package com.example.jla.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.jla.data.remote.OpenMeteoApi
import com.example.jla.data.remote.model.ApiResponse
import com.example.jla.domain.model.WeatherDetails
import com.example.jla.domain.repository.MapRepository
import com.google.ai.client.generativeai.GenerativeModel
import retrofit2.HttpException

class MapRepositoryImpl(
    private val openMeteoApi: OpenMeteoApi,
    private val generativeAi: GenerativeModel
) : MapRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherDetails(
        latitude: Double,
        longitude: Double
    ): ApiResponse<WeatherDetails, Exception> {
        return try {
            val weatherMeteoResponse =
                openMeteoApi.getCurrentWeather(latitude.toFloat(), longitude.toFloat())
            val weatherDetails = WeatherDetails(
                timeZone = weatherMeteoResponse.timezone,
                temperature = weatherMeteoResponse.currentWeather.temperature,
                weatherCode = weatherMeteoResponse.currentWeather.weathercode,
                windSpeed = weatherMeteoResponse.currentWeather.windspeed,
                time = weatherMeteoResponse.currentWeather.time
            )
            ApiResponse.Success(weatherDetails)
        } catch (e: Exception) {
            ApiResponse.Error(e, errorMessage = e.message ?: "Unknown Error")
        } catch (e: HttpException) {
            ApiResponse.Error(e, errorMessage = e.message ?: "Unknown Error")
        }
    }

    override suspend fun getGenerativeAiResponse(prompt: String): ApiResponse<String, Exception> {
        return try {
            val response = generativeAi.generateContent(prompt)
            ApiResponse.Success(response.text ?: "")
        } catch (e: Exception) {
            ApiResponse.Error(e, errorMessage = e.message ?: "Unknown Error")
        }
    }

}