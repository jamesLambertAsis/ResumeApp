package com.example.jla.presentation.screens.my_apps.maps

import com.example.jla.domain.model.LocationDetails
import com.example.jla.domain.model.WeatherDetails

interface MapUiState {

    object Idle: MapUiState
    object Loading: MapUiState
    object LoadingWeatherDetails: MapUiState
    object LoadingAiResponse: MapUiState
    data class SuccessWeatherDetails(val data: WeatherDetails): MapUiState
    object LoadingLocationDetails: MapUiState
    data class SuccessLocationDetails(val data: LocationDetails): MapUiState
    data class Error(val error: String): MapUiState

    data class LocationAnalysis(val analysis: String): MapUiState
    data class LocationAnalysisError(val error: String): MapUiState


}