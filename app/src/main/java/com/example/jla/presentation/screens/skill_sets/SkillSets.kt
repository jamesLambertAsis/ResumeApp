package com.example.jla.presentation.screens.skill_sets

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.jla.R
import com.example.jla.presentation.screens.skill_sets.composable.SkillText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.drop

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun SkillSets(
    modifier: Modifier = Modifier,
    dismiss: () -> Unit
) {

    val configuration = LocalConfiguration.current

    val dark = isSystemInDarkTheme()
    val color = if (dark) Color. White else Color. Black

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val width = screenWidth / 2
    val height = screenHeight / 2

    val optionWidth = remember { mutableStateOf(width + 180.dp) }
    val optionHeight = remember { mutableStateOf(height - 130.dp) }

    //skill variables
    val topLeftSkillShow = remember { mutableStateOf(false) }
    val topRightSkillShow = remember { mutableStateOf(false) }
    val bottomLeftSkillShow = remember { mutableStateOf(false) }
    val bottomRightSkillShow = remember { mutableStateOf(false) }

    val showCanvas = remember { mutableStateOf(true) }

    //animation variables
    var isRotated by rememberSaveable { mutableStateOf(true) }
    val boxRotationAngle by animateFloatAsState(
        targetValue = if (isRotated) 360F else 0f,
        animationSpec = tween(durationMillis = 500), label = ""
    )
    val textRotationAngle by animateFloatAsState(
        targetValue = if (isRotated) 360F else 0f,
        animationSpec = tween(durationMillis = 1000), label = ""
    )
    val textRotationAngle1 by animateFloatAsState(
        targetValue = if (!isRotated) 360F else 0f,
        animationSpec = tween(durationMillis = 1000), label = ""
    )

    LaunchedEffect(Unit) {
        snapshotFlow { isRotated }
            .drop(1) //drop initial recomposition
            .collect {
                showCanvas.value = false
                optionWidth.value = width + 250.dp
                optionHeight.value = height - 50.dp
                delay(1150)
                showCanvas.value = true
                optionWidth.value = width + 180.dp
                optionHeight.value = height - 130.dp
            }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, _ ->
                    dismiss()
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier
                .height(100.dp)
                .background(Color(0xFF3F8EFC))
                .fillMaxWidth()
            )
        }
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        color = Color(0xFF3F8EFC)
                    )
                ) {
                    append("S")
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = color
                    )
                ) {
                    append("kill ")
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        color = Color(0xFF3F8EFC)
                    )
                ) {
                    append("S")
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = color
                    )
                ) {
                    append("ets ")
                }
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 130.dp),
            color = color
        )
        Box(
            modifier = Modifier
                .width(width + 180.dp)
                .height(height + 70.dp)
                .align(Alignment.Center)
                .zIndex(1f)
        ) {

            if (topLeftSkillShow.value) {
                Column(
                    modifier = Modifier.align(Alignment.TopStart),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp
                                )
                            ) {
                                append("HTML, CSS, PHP, JavaScript, Sharepoint Online \n")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 14.sp
                                )
                            ) {
                                append("Note: This skill sets was only used in college.")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp),
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_caret_down),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight
                    )
                }
            }

            if (topRightSkillShow.value) {
                Column(
                    modifier = Modifier.align(Alignment.TopEnd),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp
                                )
                            ) {
                                append("MySQL, Firebase, Couchbase Lite, Couchbase, RoomDB \n")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp),
                        textAlign = TextAlign.End
                    )
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(id = R.drawable.ic_caret_down),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        alignment = Alignment.BottomEnd
                    )
                }
            }

            if (bottomLeftSkillShow.value) {
                Column(
                    modifier = Modifier.align(Alignment.BottomStart),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(id = R.drawable.ic_caret_up),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        alignment = Alignment.TopStart
                    )
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp
                                )
                            ) {
                                append("Java, Kotlin, XML, Jetpack Compose \n")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp),
                    )

                }
            }

            if (bottomRightSkillShow.value) {
                Column(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(id = R.drawable.ic_caret_up),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        alignment = Alignment.TopEnd
                    )
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp
                                )
                            ) {
                                append("MVP, MVI, DexGuard, Dagger Hilt, Retrofit \n")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp),
                        textAlign = TextAlign.End
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .width(optionWidth.value)
                .height(optionHeight.value)
                .align(Alignment.Center)
                .rotate(boxRotationAngle)
                .zIndex(1f)
        ) {
            SkillText(
                modifier = Modifier
                    .rotate(textRotationAngle)
                    .align(Alignment.TopStart),
                text = "Web Development",
                color = color
            ) {
                topLeftSkillShow.value = !topLeftSkillShow.value
                topRightSkillShow.value = false
            }

            SkillText(
                modifier = Modifier
                    .rotate(textRotationAngle1)
                    .align(Alignment.TopEnd),
                text = "Database Mngmt",
                color = color
            ) {
                topRightSkillShow.value = !topRightSkillShow.value
                topLeftSkillShow.value = false
            }
            SkillText(
                modifier = Modifier
                    .rotate(textRotationAngle)
                    .align(Alignment.BottomStart),
                text = "Native Android",
                color = color
            ) {
                bottomLeftSkillShow.value = !bottomLeftSkillShow.value
                bottomRightSkillShow.value = false
            }

            SkillText(
                modifier = Modifier
                    .rotate(textRotationAngle1)
                    .align(Alignment.BottomEnd),
                text = "Archt. & tools in Android",
                color = color
            ) {
                bottomRightSkillShow.value = !bottomRightSkillShow.value
                bottomLeftSkillShow.value = false
            }
        }

        Box(
            modifier = Modifier
                .width(width + 70.dp)
                .height(height - 200.dp)
                .align(Alignment.Center)
                .zIndex(-1f)
        ) {
            if (showCanvas.value) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    drawLine(
                        start = Offset(x = canvasWidth, y = 0f),
                        end = Offset(x = 0f, y = canvasHeight),
                        color = color,
                        strokeWidth = 6f
                    )
                    drawLine(
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = canvasWidth, y = canvasHeight),
                        color = color,
                        strokeWidth = 6f
                    )
                }
            }
        }

        Box(
            modifier = modifier
                .width(width)
                .height(height / 2)
                .align(Alignment.Center)
        ) {
            Image(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxSize(.8f)
                    .padding(bottom = 14.dp)
                    .align(Alignment.Center)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) {
                        isRotated = !isRotated
                        topLeftSkillShow.value = false
                        topRightSkillShow.value = false
                        bottomLeftSkillShow.value = false
                        bottomRightSkillShow.value = false
                    },
                painter = painterResource(id = R.drawable.brain_no_bg_center),
                contentDescription = null,
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 44.dp)
                .zIndex(2f),
            text = "Swipe to exit",
            fontStyle = FontStyle.Italic
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .zIndex(1f)
        ) {
            Spacer(modifier = Modifier
                .height(2.dp)
                .background(Color.Black)
                .fillMaxWidth()
            )
            Spacer(modifier = Modifier
                .height(100.dp)
                .background(Color(0xFF3F8EFC))
                .fillMaxWidth()
            )
        }
//        Row (
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.Bottom,
//        ){
//            var i = 0
//            while (i < 13) {
//                BlackLightBulb(
//                    height = (120..170).random().dp,
//                    width = (2..4).random().dp,
//                    isUpsideDown = false,
//                    size = (10..30).random().dp
//                )
//                Spacer(modifier = Modifier.width((10..16).random().dp))
//                i++
//            }
//        }
    }
}