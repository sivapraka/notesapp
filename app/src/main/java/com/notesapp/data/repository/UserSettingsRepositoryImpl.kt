package com.notesapp.data.repository


import com.notesapp.data.datasource.PreferenceManager
import com.notesapp.data.model.UserSettings
import com.notesapp.domain.repository.UserSettingsRepository
import com.notesapp.presentation.profile.FontOption
import com.notesapp.presentation.profile.ThemeOption
import kotlinx.coroutines.flow.Flow

class UserSettingsRepositoryImpl(
    private val preferenceManager: PreferenceManager
) : UserSettingsRepository {

    override val userSettings: Flow<UserSettings> = preferenceManager.preferencesFlow

    override suspend fun saveSettings(theme: ThemeOption, font: FontOption) {
        preferenceManager.savePreferences(theme, font)
    }
}