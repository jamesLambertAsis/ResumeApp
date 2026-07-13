package com.example.jla.presentation.screens.my_apps.maps


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jla.domain.model.LocationDetails
import com.example.jla.domain.model.WeatherDetails
import com.example.jla.presentation.screens.my_apps.composable.CustomLoadingDialog
import com.example.jla.presentation.screens.my_apps.utils.ShowToast
import com.example.jla.ui.theme.IndigoBlue
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

@Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
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
    var detailsMsg by remember { mutableStateOf("") }

    val isMapLoaded = remember { mutableStateOf(false) }

    val isWeatherDetailsLoaded = remember { mutableStateOf(false) }
    val isLocationDetailsLoaded = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val searchText = remember { mutableStateOf("") }

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
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ){
            if (isMapLoaded.value.not()) {
                CustomLoadingDialog()
            }
            Row(
                Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(.9f)
                    .zIndex(10f)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(24.dp),
                        clip = false // Ensures the shadow isn't cut off
                    )
            ) {
                TextField(
                    value = searchText.value,
                    onValueChange = { searchText.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            RoundedCornerShape(24.dp)
                        ),
                    placeholder = { Text("Search location...", color = Color.Gray) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_search),
                            contentDescription = "Search",
                            tint = IndigoBlue
                        )
                    },
                    trailingIcon = {
                        if (searchText.value.isNotEmpty()) {
                            IconButton(onClick = {
                                viewModel.onEvent(MapEvent.OnSearchLocationName(searchText.value, context))
                            }) {
                                Icon(
                                    painter = painterResource(id = android.R.drawable.ic_menu_send),
                                    contentDescription = "Submit",
                                    tint = IndigoBlue
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (searchText.value.isNotBlank()) {
                                viewModel.onEvent(MapEvent.OnSearchLocationName(searchText.value, context))
                            }
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        // Remove the underline
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,

                        cursorColor = RoyalBlue,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )
            }
            GoogleMap(
                modifier = Modifier
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

                else -> {
                    if (uiState == MapUiState.LoadingLocationDetails ||
                        uiState == MapUiState.LoadingWeatherDetails
                        || uiState == MapUiState.LoadingAiResponse) {
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(color = RoyalBlue)
                            Text(detailsMsg)
                        }
                    }
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
        MapUiState.Loading -> {
            CustomLoadingDialog()
        }
        MapUiState.LoadingLocationDetails -> {
            isLocationDetailsLoaded.value = false
            detailsMsg = "Getting location details"
        }

        MapUiState.LoadingWeatherDetails -> {
            isWeatherDetailsLoaded.value = false
            detailsMsg = "Getting weather details"
        }

        MapUiState.LoadingAiResponse -> {
            detailsMsg = "Generating A.I response"
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


