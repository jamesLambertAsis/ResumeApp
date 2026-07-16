package com.example.jla.presentation.screens.my_apps

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jla.R
import com.example.jla.presentation.screens.my_apps.composable.DialogChatLogIn
import com.example.jla.presentation.screens.my_apps.composable.MyAppsItem
import com.example.jla.presentation.screens.my_apps.utils.isLocationEnabled
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyApps(
    myAppsViewModel: MyAppsViewModel = koinViewModel(),
    toChatScreen: () -> Unit,
    toWebViewScreen: () -> Unit,
    toMapScreen: () -> Unit,
    back: () -> Unit
) {

    val showDialog = remember {
        mutableStateOf(false)
    }
    val userName = remember {
        mutableStateOf("")
    }
    val mobileNo = remember {
        mutableStateOf("")
    }
    val askGpsEnable = remember {
        mutableStateOf(false)
    }
    val state by myAppsViewModel.state.collectAsStateWithLifecycle()

    val errorMsg = remember { mutableStateOf("") }

    val context = LocalContext.current

    val hasPermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            toMapScreen()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                Modifier
                    .padding(18.dp)
                    .clickable(
                        interactionSource = null,
                        indication = null
                    ) {
                        back()
                    }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "My Applications",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
        ) {

            MyAppsItem(
                item = "Chats",
                icon = painterResource(id = R.drawable.chat_bubbles_with_ellipsis),
                modifier = Modifier.weight(1f)
            ) {
                showDialog.value = !showDialog.value
            }
            Spacer(modifier = Modifier.height(16.dp))
            MyAppsItem(
                item = "Web View",
                icon = painterResource(id = R.drawable.internet),
                modifier = Modifier.weight(1f)
            ) {
                toWebViewScreen()
            }
            Spacer(modifier = Modifier.height(16.dp))
            MyAppsItem(
                item = "World Clock",
                icon = painterResource(id = R.drawable.location),
                modifier = Modifier.weight(1f)
            ) {
                if (isLocationEnabled(context).not()) {
                    askGpsEnable.value = true
                    return@MyAppsItem
                }
                if (hasPermission.not()) {
                    permissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    return@MyAppsItem
                }
                toMapScreen()

            }
        }
    }

    if (askGpsEnable.value) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Enable Location") },
            text = { Text("Please turn on location services to use this feature.") },
            confirmButton = {
                Button(onClick = {
                    context.startActivity(
                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    )
                    askGpsEnable.value = false
                }) {
                    Text("Go to Settings")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        askGpsEnable.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showDialog.value) {
        DialogChatLogIn(
            mobileOnValueChange = {
                mobileNo.value = it
            },
            userOnValueChange = {
                userName.value = it
            },
            errorMsg = errorMsg.value,
            isLoading = state is MyAppsState.Loading,
            onCancel = {
                showDialog.value = false
            },
            onSignUp = {
                myAppsViewModel.onEvent(
                    MyAppsEvent.SignUp(
                        mobileNum = mobileNo.value,
                        userName = userName.value
                    )
                )
            },
            onLogIn = {
                myAppsViewModel.onEvent(
                    MyAppsEvent.LogIn(
                        mobileNum = mobileNo.value,
                        userName = userName.value
                    )
                )
            }
        )
    }
    LaunchedEffect(state) {
        when (state) {
            is MyAppsState.Error -> {
                errorMsg.value = (state as MyAppsState.Error).error
            }

            MyAppsState.Idle -> Unit
            MyAppsState.Loading -> Unit
            MyAppsState.LogInSuccess -> {
                showDialog.value = false
                myAppsViewModel.resetState()
                toChatScreen()
            }

            MyAppsState.SignUpSuccess -> {
                errorMsg.value = "Sign Up Success. Please Login"
            }
        }
    }
}