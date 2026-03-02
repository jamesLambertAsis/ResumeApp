package com.example.jla.presentation.screens.my_apps.maps

import android.content.Context

sealed class MapEvent{
    data class GetWeatherDetails(val latitude: Double, val longitude: Double): MapEvent()
    data class GetLocationDetails(val latitude: Double, val longitude: Double, val context: Context): MapEvent()
}

