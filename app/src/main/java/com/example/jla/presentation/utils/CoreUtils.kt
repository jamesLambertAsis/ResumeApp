package com.example.jla.presentation.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

object ThemeUtils {
    @Composable
    fun isDarkMode(): Boolean {
        return isSystemInDarkTheme()
    }
}