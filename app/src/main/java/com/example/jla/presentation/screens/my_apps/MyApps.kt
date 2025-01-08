package com.example.jla.presentation.screens.my_apps

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jla.R
import com.example.jla.domain.model.User
import com.example.jla.presentation.screens.my_apps.composable.DialogChatLogIn
import com.example.jla.presentation.screens.my_apps.composable.MyAppsItem
import com.example.jla.presentation.screens.my_apps.utils.ChatUtils
import com.example.jla.presentation.screens.my_apps.utils.LogInResult
import org.koin.androidx.compose.koinViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MyApps(
    myAppsViewModel: MyAppsViewModel = koinViewModel(),
    toChatScreen: () -> Unit,
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
    val verifyMsg = myAppsViewModel.verifyMsg.value

    val state = myAppsViewModel.state.value

    LaunchedEffect(verifyMsg) {
        if (verifyMsg == LogInResult.SUCCESS) {
            showDialog.value = false
            myAppsViewModel.resetVerifyMsg()
            ChatUtils.loggedInUserName = userName.value
            toChatScreen()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 44.dp)
        ) {
            Column(
                Modifier
                    .padding(14.dp)
                    .clickable {
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
                item = "Message",
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
            )

            Spacer(modifier = Modifier.height(16.dp))

            MyAppsItem(
                item = "A.I",
                icon = painterResource(id = R.drawable.robot),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            MyAppsItem(
                item = "World Clock",
                icon = painterResource(id = R.drawable.location),
                modifier = Modifier.weight(1f)
            )

        }
    }

    if (showDialog.value){
        DialogChatLogIn(
            mobileOnValueChange = {
                mobileNo.value = it
            },
            userOnValueChange = {
                userName.value = it
            },
            errorMsg = verifyMsg,
            isLoading = state.isLoading,
            onCancel = {
                showDialog.value = false
                myAppsViewModel.resetVerifyMsg()
            },
            onSignUp = {
                myAppsViewModel.signUp(
                    User(
                        mobileNo = mobileNo.value,
                        userName = userName.value
                    )
                )
            },
            onLogIn = {
                myAppsViewModel.logIn(
                    User(
                        mobileNo = mobileNo.value,
                        userName = userName.value
                    )
                )
            }
        )
    }
}