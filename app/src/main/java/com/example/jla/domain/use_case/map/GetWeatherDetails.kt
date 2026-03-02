package com.example.jla.domain.use_case.map

import com.example.jla.core.TaskResult
import com.example.jla.data.remote.model.ApiResponse
import com.example.jla.domain.model.WeatherDetails
import com.example.jla.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWeatherDetails(
    private val mapRepository: MapRepository
) {

    operator fun invoke(latitude: Double, longitude: Double): Flow<TaskResult<WeatherDetails>> = flow {
        emit(TaskResult.Loading)
        when (val response = mapRepository.getWeatherDetails(latitude, longitude)) {
            is ApiResponse.Error<*> -> {
                emit(TaskResult.Error(errorMessage = response.errorMessage ?: "Unknown Error"))
            }
            is ApiResponse.Success<WeatherDetails> -> {
                emit(TaskResult.Success(response.data))
            }
        }
    }

}