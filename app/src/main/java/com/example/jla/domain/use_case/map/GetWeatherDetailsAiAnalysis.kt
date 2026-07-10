package com.example.jla.domain.use_case.map

import com.example.jla.core.TaskResult
import com.example.jla.data.remote.model.ApiResponse
import com.example.jla.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWeatherDetailsAiAnalysis(
    private val mapRepository: MapRepository
) {

    operator fun invoke(prompt: String): Flow<TaskResult<String>> = flow {
        emit(TaskResult.Loading)
        when (val response = mapRepository.getGenerativeAiResponse(prompt)) {
            is ApiResponse.Error<*> -> {
                emit(TaskResult.Error(errorMessage = response.errorMessage ?: "Unknown Error"))
            }
            is ApiResponse.Success<String> -> {
                emit(TaskResult.Success(response.data))
            }
        }
    }

}