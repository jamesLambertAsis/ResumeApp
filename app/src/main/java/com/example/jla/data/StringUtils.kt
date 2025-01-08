package com.example.jla.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

class StringUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun encodeToBase64(input: String): String {
        val bytes = input.toByteArray()
        return java.util.Base64.getEncoder().encodeToString(bytes)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decodeFromBase64(base64String: String): String {
        LoremIpsum().toString()
        val bytes = java.util.Base64.getDecoder().decode(base64String)
        return String(bytes)
    }

}