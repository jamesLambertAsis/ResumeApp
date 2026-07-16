package com.example.jla.presentation.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight

fun String.markdownToAnnotatedString(): AnnotatedString {
    val regex = "\\*\\*(.*?)\\*\\*".toRegex()

    return buildAnnotatedString {
        var lastIndex = 0

        regex.findAll(this@markdownToAnnotatedString).forEach { match ->
            append(this@markdownToAnnotatedString.substring(lastIndex, match.range.first))

            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(match.groupValues[1])
            pop()

            lastIndex = match.range.last + 1
        }

        append(this@markdownToAnnotatedString.substring(lastIndex))
    }
}