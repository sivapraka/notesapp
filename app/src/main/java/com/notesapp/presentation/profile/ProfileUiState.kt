package com.notesapp.presentation.profile

data class ProfileUiState(
    val selectedTheme: ThemeOption = ThemeOption.SYSTEM,
    val selectedFont: FontOption = FontOption.DEFAULT
)