package com.notesapp.presentation.profile

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable


enum class ThemeOption {
    LIGHT, DARK, SYSTEM
}

@Composable
fun ThemeOption.isDarkTheme(): Boolean {
    return when (this) {
        ThemeOption.DARK -> true
        ThemeOption.LIGHT -> false
        ThemeOption.SYSTEM -> isSystemInDarkTheme()
    }
}