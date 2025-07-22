package com.notesapp.data.model

import com.notesapp.presentation.profile.FontOption
import com.notesapp.presentation.profile.ThemeOption


data class UserSettings(
    val theme: ThemeOption = ThemeOption.SYSTEM,
    val font: FontOption = FontOption.DEFAULT
)
