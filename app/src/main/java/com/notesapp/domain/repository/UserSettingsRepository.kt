package com.notesapp.domain.repository

import com.notesapp.data.model.UserSettings
import com.notesapp.presentation.profile.FontOption
import com.notesapp.presentation.profile.ThemeOption
import kotlinx.coroutines.flow.Flow


interface UserSettingsRepository {
    val userSettings: Flow<UserSettings>
    suspend fun saveSettings(theme: ThemeOption, font: FontOption)
}