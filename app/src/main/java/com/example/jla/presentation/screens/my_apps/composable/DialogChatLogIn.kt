package com.example.jla.presentation.screens.my_apps.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.jla.R
import com.example.jla.ui.theme.IndigoBlue

@Composable
fun DialogChatLogIn(
    mobileOnValueChange: (String) -> Unit,
    userOnValueChange: (String) -> Unit,
    errorMsg: String = "",
    isLoading: Boolean = false,
    onSignUp: () -> Unit = {},
    onLogIn: () -> Unit = {},
    onCancel: () -> Unit = {}
) {

    Dialog(onDismissRequest = { onCancel() }) {

        val mobile = remember {
            mutableStateOf("")
        }
        val user = remember {
            mutableStateOf("")
        }
        val error = remember {
            mutableStateOf(errorMsg)
        }

        val showLoading = remember {
            mutableStateOf(isLoading)
        }

        LaunchedEffect(errorMsg) {
            error.value = errorMsg
        }
        LaunchedEffect(isLoading) {
            showLoading.value = isLoading
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp))
                .background(Color.White)
                .padding(top = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Welcome to Chats!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(.9f),
                enabled = !showLoading.value,
                value = mobile.value,
                onValueChange = {
                    mobile.value = it.take(15)
                    mobileOnValueChange(mobile.value)
                },
                singleLine = true,
                placeholder = { Text(text = "Enter mobile number") },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.ic_phone),
                        contentDescription = null,
                        tint = Color.Black
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(.9f),
                value = user.value,
                enabled = !showLoading.value,
                onValueChange = {
                    user.value = it.take(15)
                    userOnValueChange(user.value)
                },
                singleLine = true,
                placeholder = { Text(text = "Enter username") },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.ic_account),
                        contentDescription = null,
                        tint = Color.Black,
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            )
            if (showLoading.value) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(.9f),
                ) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .background(Color.White)
                            .padding(start = 4.dp, end = 4.dp),
                        text = "Verify",
                        color = Color.Gray
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(.9f),
                    text = if (error.value.isNotBlank() && error.value.isNotEmpty()) "*${error.value}*" else "",
                    color = Color.Red,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Start
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(.9f),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (mobile.value.isEmpty() || user.value.isEmpty()) {
                                error.value = "Please don't leave a blank field"
                                return@clickable
                            } else if (showLoading.value) {
                                return@clickable
                            }
                            onSignUp()
                        }
                        .padding(10.dp),
                    text = "Sign Up",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (mobile.value.isEmpty() || user.value.isEmpty()) {
                            error.value = "Please don't leave a blank field"
                            return@clickable
                        } else if (showLoading.value) {
                            return@clickable
                        }
                        onLogIn()
                    }
                        .padding(10.dp),
                    text = "LogIn",
                    fontWeight = FontWeight.Bold,
                    color = Color(IndigoBlue.value)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}