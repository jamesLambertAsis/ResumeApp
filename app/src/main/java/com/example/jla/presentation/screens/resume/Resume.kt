package com.example.jla.presentation.screens.resume

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.jla.R
import com.example.jla.presentation.screens.resume.composable.EducationContent
import com.example.jla.presentation.screens.resume.composable.MoreInfoDialog
import com.example.jla.presentation.screens.resume.composable.PersonalInfoContent
import com.example.jla.presentation.screens.resume.composable.PersonalTraitContent
import com.example.jla.presentation.screens.resume.composable.WorkExperienceContent

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun Resume(modifier: Modifier = Modifier, dismissScreen: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, _ ->
                    dismissScreen()
                }
            }
    ) {
        val dialogTitle = remember {
            mutableStateOf("")
        }
        val showContentOption = remember {
            mutableStateOf("")
        }
        val headerColor = remember {
            mutableStateOf(Color.Red)
        }
        val showDialog = remember {
            mutableStateOf(false)
        }
        //start of name and profile picture
        Box(
            Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(bottom = 10.dp)
                .weight(.8f)
        ) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(230.dp)
                    .clip(shape = RoundedCornerShape(0.dp, 350.dp, 350.dp, 0.dp))
                    .background(Color(0xFF3B28CC)),
            ) {
                Box(
                    modifier = Modifier
                        .width(170.dp)
                        .height(170.dp)
                        .clip(shape = CircleShape)
                        .align(Alignment.Center)
                        .border(8.dp, Color.DarkGray, CircleShape)
                        .background(Color.White)
                ) {
                    Image(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(start = 18.dp)
                            .align(Alignment.CenterEnd),
                        painter = painterResource(id = R.drawable.sample_profile_no_bg_1x1),
                        contentDescription = null
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp)
                    .background(Color(0xFF3B28CC))
                    .zIndex(-1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        ) {
                            append("James Lambert O. Asis \n")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        ) {
                            append("Android Developer")
                        }
                    },
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                )
            }
        }
        //end of name and profile picture

        HorizontalDivider()
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .weight(.4f),
            text = "To work in challenging environment that would lead to the fulfillment of " +
                    "both corporate and personal goals.",
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
        )
        HorizontalDivider()

        //start of 1st row options
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )
            Box(
                modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .weight(1f)
                    .background(Color(0xFFADD7F6))
                    .padding(30.dp)
                    .fillMaxHeight(.8f)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        dialogTitle.value = "Personal Info"
                        showDialog.value = true
                        showContentOption.value = ResumeOption.PERSONAL_INFO.name
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.ic_personal_info),
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            Spacer(
                modifier = Modifier
                    .width(14.dp)
            )
            Box(
                modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .weight(1f)
                    .background(Color(0xFF2667FF))
                    .padding(26.dp)
                    .fillMaxHeight(.8f)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        dialogTitle.value = "Work Experience"
                        showDialog.value = true
                        showContentOption.value = ResumeOption.WORK_EXPERIENCE.name
                    }
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "Work\n\nExperience",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )
        } //end of 1st row options

        //start of 2nd row options
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .weight(1f)
                .fillMaxHeight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )
            Box(
                modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .weight(1f)
                    .background(Color(0xFF2667FF))
                    .padding(26.dp)
                    .fillMaxHeight(.8f)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        dialogTitle.value = "Personal Trait"
                        showDialog.value = true
                        showContentOption.value = ResumeOption.PERSONAL_TRAIT.name
                    }
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "Personal\n\nTraits",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(
                modifier = Modifier
                    .width(14.dp)
            )
            Box(
                modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .weight(1f)
                    .background(Color(0xFFADD7F6))
                    .padding(30.dp)
                    .fillMaxHeight(.8f)
            ) {
                Icon(
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.Center)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            dialogTitle.value = "Education History"
                            showDialog.value = true
                            showContentOption.value = ResumeOption.EDUCATION_HISTORY.name
                        },
                    painter = painterResource(id = R.drawable.ic_education_history),
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )
        }//end of 2nd row options

        Text(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .fillMaxWidth(),
            text = "Swipe to exit",
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center
        )

        if (showDialog.value) {
            MoreInfoDialog(
                title = dialogTitle.value,
                headerColor = headerColor.value,
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {
                        HorizontalDivider()
                        when (showContentOption.value) {
                            ResumeOption.PERSONAL_INFO.name -> {
                                PersonalInfoContent(
                                    Modifier.padding(
                                        start = 8.dp,
                                        end = 4.dp,
                                        top = 18.dp
                                    )
                                )
                                headerColor.value = Color(0xFFADD7F6)
                            }

                            ResumeOption.WORK_EXPERIENCE.name -> {
                                WorkExperienceContent(
                                    Modifier.padding(
                                        start = 8.dp,
                                        end = 6.dp,
                                        top = 18.dp
                                    )
                                )
                                headerColor.value = Color(0xFF2667FF)
                            }

                            ResumeOption.PERSONAL_TRAIT.name -> {
                                PersonalTraitContent(
                                    Modifier.padding(
                                        start = 8.dp,
                                        end = 6.dp,
                                        top = 18.dp
                                    )
                                )
                                headerColor.value = Color(0xFF2667FF)
                            }

                            ResumeOption.EDUCATION_HISTORY.name -> {
                                EducationContent(
                                    Modifier.padding(
                                        start = 8.dp,
                                        end = 6.dp,
                                        top = 18.dp
                                    )
                                )
                                headerColor.value = Color(0xFFADD7F6)
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 18.dp)
                        )
                    }
                }
            ) {
                showDialog.value = false
            }
        }
    }
}

enum class ResumeOption {
    PERSONAL_INFO, WORK_EXPERIENCE, PERSONAL_TRAIT, EDUCATION_HISTORY
}