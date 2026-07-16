package com.example.jla.presentation.screens.my_apps.maps

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.example.jla.core.BaseViewModel
import com.example.jla.core.TaskResult
import com.example.jla.domain.model.LocationDetails
import com.example.jla.domain.model.WeatherDetails
import com.example.jla.domain.use_case.map.MapUseCase
import kotlinx.coroutines.delay
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
                        locationName = null,
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
                    getWeatherDetails(event.latitude, event.longitude)
                }
            }

            is MapEvent.GetAiAnalysis -> {
                run {
                    mapUseCase.getWeatherAiAnalysis(
                        "Your a weather forecaster give suggestion to weather using all of this data and" +
                                "state openmeteo api is the source of weather details and what is your name that's gemini AI also use this ${event.weatherDetails.formattedTime} as you great in the first sentence" +
                                "${event.locationDetails.locality} ${event.weatherDetails.weatherCodeDescription} ${event.weatherDetails.temperature}"
                    ).collect { result ->
                        when (result) {
                            is TaskResult.Error<*> -> {
                                _state.value = MapUiState.LocationAnalysisError(result.errorMessage)
                            }

                            TaskResult.Loading -> {
                                _state.value = MapUiState.LoadingAiResponse
                            }

                            is TaskResult.Success<String> -> {
                                _state.value = MapUiState.LocationAnalysis(result.data)
                            }
                        }
                    }
                }
            }

            is MapEvent.OnSearchLocationName -> {
                run {
                    getLocationDetails(
                        latitude = 0.0,
                        longitude = 0.0,
                        locationName = event.locationName,
                        context = event.context
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
                                delay(300)//give time for UI to update
                                val latitude = result.data.latitude?.toDouble() ?: 0.0
                                val longitude = result.data.longitude?.toDouble() ?: 0.0
                                getWeatherDetails(latitude, longitude)
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun getWeatherDetails(
        latitude: Double,
        longitude: Double
    ) {
        mapUseCase.getWeatherDetails(latitude, longitude)
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
    private fun getLocationDetails(
        latitude: Double,
        longitude: Double,
        locationName: String?,
        context: Context
    ): Flow<TaskResult<LocationDetails>> = flow {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val locationDetails = if (locationName.isNullOrEmpty().not()) {
                val searchedLocation = geocoder.getLocationDetailFromName(locationName)
                geocoder.getLocationDetail(searchedLocation?.latitude?.toDouble() ?: 0.0, searchedLocation?.longitude?.toDouble() ?: 0.0)
            } else {
                geocoder.getLocationDetail(latitude, longitude)
            }
            emit(TaskResult.Success(locationDetails))
        } catch (e: Exception) {
            emit(TaskResult.Error(errorMessage = e.message ?: "Unknown Error"))
        }
    }

    private suspend fun Geocoder.getLocationDetailFromName(
        locationName: String
    ): LocationDetails? =
        suspendCancellableCoroutine { task ->
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    getFromLocationName(locationName, 1) { addresses ->
                        addresses.firstOrNull()?.let {
                            task.resume(mapAddressToLocationDetails(it))
                        } ?: task.resumeWithException(Exception("No Details Found"))
                    }
                } else {
                    val addresses = getFromLocationName(locationName, 1)
                    addresses?.firstOrNull()?.let {
                        task.resume(mapAddressToLocationDetails(it))
                    } ?: task.resumeWithException(Exception("No Details Found"))
                }
            } catch (e: Exception) {
                task.resumeWithException(Exception(e))
            }
        }

    private suspend fun Geocoder.getLocationDetail(
        latitude: Double,
        longitude: Double,
    ): LocationDetails =
        suspendCancellableCoroutine { task ->
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    getFromLocation(
                        latitude, longitude, 1
                    ) { address ->
                        address.firstOrNull()?.let {
                            task.resume(mapAddressToLocationDetails(it))
                        } ?: task.resumeWithException(Exception("No Details Found"))

                    }
                } else {
                    val address = getFromLocation(latitude, longitude, 1)
                    address?.firstOrNull()?.let {
                        task.resume(mapAddressToLocationDetails(it))
                    } ?: task.resumeWithException(Exception("No Details Found"))
                }
            } catch (e: Exception) {
                task.resumeWithException(Exception(e))
            }
        }

    private fun mapAddressToLocationDetails(detail: Address): LocationDetails {
        return LocationDetails(
            addressLines = listOf(detail.getAddressLine(0) ?: ""),
            admin = detail.adminArea ?: "",
            suhAdmin = detail.subAdminArea ?: "",
            locality = detail.locality ?: "",
            thoroughFare = detail.thoroughfare ?: "",
            postalCode = detail.postalCode ?: "",
            countryCode = detail.countryCode ?: "",
            countryName = detail.countryName ?: "",
            latitude = detail.latitude.toString(),
            longitude = detail.longitude.toString(),
        )
    }
}