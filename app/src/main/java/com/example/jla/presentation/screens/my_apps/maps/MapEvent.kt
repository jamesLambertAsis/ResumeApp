package com.example.jla.presentation.screens.my_apps.maps

import android.content.Context
import com.example.jla.domain.model.LocationDetails
import com.example.jla.domain.model.WeatherDetails

interface MapEvent{
    data class GetWeatherDetails(val latitude: Double, val longitude: Double): MapEvent
    data class GetLocationDetails(val latitude: Double, val longitude: Double, val context: Context): MapEvent
    data class GetAiAnalysis(val locationDetails: LocationDetails, val weatherDetails: WeatherDetails): MapEvent
    data class OnSearchLocationName(val locationName: String, val context: Context): MapEvent
}

