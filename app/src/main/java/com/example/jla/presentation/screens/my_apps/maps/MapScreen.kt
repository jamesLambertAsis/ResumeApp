package com.example.jla.presentation.screens.my_apps.maps


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jla.domain.model.LocationDetails
import com.example.jla.domain.model.WeatherDetails
import com.example.jla.presentation.screens.my_apps.composable.CustomLoadingDialog
import com.example.jla.presentation.screens.my_apps.utils.ShowToast
import com.example.jla.presentation.screens.my_apps.utils.WeatherUtils
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
    val locationDetails = remember { mutableStateOf(LocationDetails()) }
    val weatherDetails = remember { mutableStateOf(WeatherDetails()) }

    val isMapLoaded = remember { mutableStateOf(false) }

    val isWeatherDetailsLoaded = remember { mutableStateOf(false) }
    val isLocationDetailsLoaded = remember { mutableStateOf(false) }

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

    Box(
        Modifier
            .fillMaxSize()
    ) {
        if (isMapLoaded.value.not()) {
            CustomLoadingDialog()
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            onMapLoaded = {
                Log.d("xxx-->", "MapScreen: map loaded")
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
            Modifier
                .wrapContentWidth()
                .align(Alignment.BottomStart)
                .zIndex(100f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Country: ", color = Color.Black)
                if (isLocationDetailsLoaded.value.not()) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(14.dp)
                    )
                } else {
                    locationDetails.value.countryName?.let { Text(it, color = Color.Black) }
                }

            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Locality: ", color = Color.Black)
                if (isLocationDetailsLoaded.value.not()) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(14.dp)
                    )
                } else {
                    Text(
                        "${locationDetails.value.admin}, ${locationDetails.value.locality}",
                        color = Color.Black
                    )
                }

            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Time: ", color = Color.Black)
                if (isWeatherDetailsLoaded.value.not()) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(14.dp)
                    )
                } else {
                    if (weatherDetails.value.time.isNotEmpty()) {
                        Log.d("xxx-->", "MapScreen: " + weatherDetails.value.time)
                        Log.d("xxx-->", "MapScreen: " + weatherDetails.value.timeZone)
                        Text(
                            weatherDetails.value.formatOpenMeteoTime(
                                weatherDetails.value.time,
                                weatherDetails.value.timeZone
                            ), color = Color.Black
                        )
                    }
                }

            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Temperature: ", color = Color.Black)
                if (isWeatherDetailsLoaded.value.not()) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(14.dp)
                    )
                } else {
                    Text("${weatherDetails.value.temperature}℃", color = Color.Black)
                }

            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Windspeed: ", color = Color.Black)
                if (isWeatherDetailsLoaded.value.not()) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(14.dp)
                    )
                } else {
                    Text("${weatherDetails.value.windSpeed}", color = Color.Black)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Weather Code: ", color = Color.Black)
                if (isWeatherDetailsLoaded.value.not()) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(14.dp)
                    )
                } else {
                    Text(
                        "${weatherDetails.value.weatherCode} | ${
                            WeatherUtils.interpretWeatherByCode(
                                weatherDetails.value.weatherCode
                            )
                        }", color = Color.Black
                    )
                }

            }
        }

    }

    when (state) {
        is MapUiState.Error -> {
            ShowToast((state as MapUiState.Error).error)
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
            locationDetails.value = (state as MapUiState.SuccessLocationDetails).data
            isLocationDetailsLoaded.value = true
        }

        is MapUiState.SuccessWeatherDetails -> {
            weatherDetails.value = (state as MapUiState.SuccessWeatherDetails).data
            isWeatherDetailsLoaded.value = true
        }
    }

}


