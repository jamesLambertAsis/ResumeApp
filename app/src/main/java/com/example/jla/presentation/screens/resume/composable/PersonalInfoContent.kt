package com.example.jla.presentation.screens.resume.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun PersonalInfoContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(

            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                    )
                ) {
                    append("Facebook: ")
                }
                append("James Lambert Osmeña Asis\n\n")

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                    )
                ) {
                    append("Date of birth: ")
                }
                append("June 9, 1997\n\n")

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                    )
                ) {
                    append("Mobile: ")
                }
                append("(+63) 99 7904 6363\n\n")

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                    )
                ) {
                    append("Email: ")
                }
                append("jameslambertyou@gmail.com")
            },
            color = Color.Black,
        )
    }
}