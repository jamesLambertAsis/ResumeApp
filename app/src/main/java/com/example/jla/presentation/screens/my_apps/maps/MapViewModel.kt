package com.example.jla.presentation.screens.my_apps.maps

import android.content.Context
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.jla.core.BaseViewModel
import com.example.jla.core.TaskResult
import com.example.jla.domain.model.LocationDetails
import com.example.jla.domain.model.WeatherDetails
import com.example.jla.domain.use_case.map.MapUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MapViewModel(
    private val mapUseCase: MapUseCase
) : BaseViewModel<MapUiState>(MapUiState.Idle) {

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.GetLocationDetails -> {
                run {
                    getLocationDetails(
                        event.latitude,
                        event.longitude,
                        event.context
                    ).collect { result ->
                        when (result) {
                            is TaskResult.Error<*> -> {
                                _state.value = MapUiState.Error(result.errorMessage)
                            }

                            TaskResult.Loading -> {
                                _state.value = MapUiState.LoadingLocationDetails
                            }

                            is TaskResult.Success<LocationDetails> -> {
                                _state.value = MapUiState.SuccessLocationDetails(result.data)
                            }
                        }
                    }
                }
            }

            is MapEvent.GetWeatherDetails -> {
                run {
                    mapUseCase.getWeatherDetails(event.latitude, event.longitude)
                        .collect { result ->
                            when (result) {
                                is TaskResult.Error<*> -> {
                                    _state.value = MapUiState.Error(result.errorMessage)
                                }

                                TaskResult.Loading -> {
                                    _state.value = MapUiState.LoadingWeatherDetails
                                }

                                is TaskResult.Success<WeatherDetails> -> {
                                    _state.value = MapUiState.SuccessWeatherDetails(result.data)
                                }
                            }
                        }
                }
            }

            is MapEvent.GetAiAnalysis -> {
                run {
                    mapUseCase.getWeatherAiAnalysis(
                        "Your a weather forecaster give suggestion to weather using all of this data and" +
                                " state openmeteo api is the source of weather details also use this ${event.weatherDetails.formattedTime} as you great in the first sentence" +
                                "${event.locationDetails.locality} ${event.weatherDetails.temperature}"
                    ).collect { result ->
                        when (result) {
                            is TaskResult.Error<*> -> {
                                _state.value = MapUiState.LocationAnalysisError(result.errorMessage)
                            }

                            TaskResult.Loading -> {
                                _state.value = MapUiState.LoadingAiResponse
                            }

                            is TaskResult.Success<String> -> {
                                _state.value = MapUiState.LocationAnalysis(event.weatherDetails.formattedTime+"\n"+result.data)
                            }
                        }
                    }
                }
            }
        }
    }

    suspend fun Geocoder.getLocationDetail(
        latitude: Double,
        longitude: Double,
    ): LocationDetails =
        suspendCancellableCoroutine { task ->


            try {
                getFromLocation(
                    latitude, longitude, 1
                ) { address ->
                    if (address.isNotEmpty()) {
                        val detail = address[0]
                        val locationDetails = LocationDetails(
                            addressLines = listOf(detail.getAddressLine(0)),
                            admin = detail.adminArea,
                            suhAdmin = detail.subAdminArea,
                            locality = detail.locality,
                            thoroughFare = detail.thoroughfare,
                            postalCode = detail.postalCode,
                            countryCode = detail.countryCode,
                            countryName = detail.countryName,
                            latitude = detail.latitude.toString(),
                            longitude = detail.longitude.toString(),
                        )
                        task.resume(locationDetails)
                    } else {
                        task.resumeWithException(Exception("No Details Found"))
                    }
                }

            } catch (e: Exception) {
                task.resumeWithException(Exception(e))
            }
        }

    private fun getLocationDetails(
        latitude: Double,
        longitude: Double,
        context: Context
    ): Flow<TaskResult<LocationDetails>> = flow {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val locationDetails = geocoder.getLocationDetail(latitude, longitude)
            emit(TaskResult.Success(locationDetails))
        } catch (e: Exception) {
            emit(TaskResult.Error(errorMessage = e.message ?: "Unknown Error"))
        }
    }
}