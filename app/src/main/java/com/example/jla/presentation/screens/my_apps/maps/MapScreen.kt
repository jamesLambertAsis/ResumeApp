@file:Suppress("COMPOSE_APPLIER_CALL_MISMATCH")

package com.example.jla.presentation.screens.my_apps.maps


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jla.domain.model.LocationDetails
import com.example.jla.domain.model.WeatherDetails
import com.example.jla.presentation.screens.my_apps.composable.CustomLoadingDialog
import com.example.jla.presentation.screens.my_apps.utils.ShowToast
import com.example.jla.ui.theme.RoyalBlue
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.tasks.await
import org.koin.androidx.compose.koinViewModel

@androidx.annotation.RequiresPermission(allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION])
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MapScreen(
    viewModel: MapViewModel = koinViewModel()
) {

    val context = LocalContext.current

    val userLocation = remember { mutableStateOf(LatLng(0.0, 0.0)) }
    val cameraPositionState = rememberCameraPositionState {}

    val client = LocationServices.getFusedLocationProviderClient(context)
    val clickedLocation = remember { mutableStateOf(LatLng(0.0, 0.0)) }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val uiState = state
    val locationDetails = remember { mutableStateOf(LocationDetails()) }
    val weatherDetails = remember { mutableStateOf(WeatherDetails()) }

    val isMapLoaded = remember { mutableStateOf(false) }

    val isWeatherDetailsLoaded = remember { mutableStateOf(false) }
    val isLocationDetailsLoaded = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    LaunchedEffect(isWeatherDetailsLoaded.value, isLocationDetailsLoaded.value) {
        if (isLocationDetailsLoaded.value && isWeatherDetailsLoaded.value) {
            viewModel.onEvent(
                MapEvent.GetAiAnalysis(
                    locationDetails = locationDetails.value,
                    weatherDetails = weatherDetails.value
                )
            )
        }
    }


    LaunchedEffect(isMapLoaded.value) {
        if (isMapLoaded.value.not()) {
            return@LaunchedEffect
        }
        val location = client.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).await()
        if (location != null) {
            userLocation.value = LatLng(location.latitude, location.longitude)
            cameraPositionState.position =
                CameraPosition.fromLatLngZoom(userLocation.value, 20f)
            viewModel.onEvent(
                MapEvent.GetWeatherDetails(
                    userLocation.value.latitude,
                    userLocation.value.longitude
                )
            )
            viewModel.onEvent(
                MapEvent.GetLocationDetails(
                    userLocation.value.latitude,
                    userLocation.value.longitude,
                    context
                )
            )
        }
    }

    Column(
        Modifier
            .fillMaxSize()
    ) {
        if (isMapLoaded.value.not()) {
            CustomLoadingDialog()
        }
        GoogleMap(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            onMapLoaded = {
                isMapLoaded.value = true
            },
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                clickedLocation.value = latLng
                viewModel.onEvent(
                    MapEvent.GetWeatherDetails(
                        clickedLocation.value.latitude,
                        clickedLocation.value.longitude
                    )
                )
                viewModel.onEvent(
                    MapEvent.GetLocationDetails(
                        clickedLocation.value.latitude,
                        clickedLocation.value.longitude,
                        context
                    )
                )
            },
            properties = MapProperties(isMyLocationEnabled = true),
            uiSettings = MapUiSettings(
                tiltGesturesEnabled = true,
                compassEnabled = true,
                myLocationButtonEnabled = true
            )
        ) {
            Marker(
                state = MarkerState(
                    position = clickedLocation.value,

                    ),
                title = locationDetails.value.locality,
                snippet = locationDetails.value.postalCode ?: ""
            )
        }
        if (isMapLoaded.value.not()) {
            return
        }
        Column(
            modifier = Modifier
                .weight(.3f)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            when (uiState) {
                is MapUiState.LocationAnalysis -> {
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        text = uiState.analysis,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                }
                is MapUiState.LoadingAiResponse -> CircularProgressIndicator(color = RoyalBlue)
                is MapUiState.LocationAnalysisError -> {
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        text = uiState.error,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                }
            }
        }
    }

    when (uiState) {
        is MapUiState.Error -> {
            ShowToast(uiState.error)
            isWeatherDetailsLoaded.value = true
            isLocationDetailsLoaded.value = true
        }

        MapUiState.Idle -> Unit
        MapUiState.Loading -> CustomLoadingDialog()
        MapUiState.LoadingLocationDetails -> {
            isLocationDetailsLoaded.value = false
        }

        MapUiState.LoadingWeatherDetails -> {
            isWeatherDetailsLoaded.value = false
        }

        is MapUiState.SuccessLocationDetails -> {
            locationDetails.value = uiState.data
            isLocationDetailsLoaded.value = true
        }

        is MapUiState.SuccessWeatherDetails -> {
            weatherDetails.value = uiState.data
            isWeatherDetailsLoaded.value = true
        }
    }

}


