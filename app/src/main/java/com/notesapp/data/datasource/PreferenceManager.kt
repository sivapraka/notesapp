package com.notesapp.data.datasource



import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.notesapp.data.model.UserSettings
import com.notesapp.presentation.profile.FontOption
import com.notesapp.presentation.profile.ThemeOption

import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Extension property for Context
private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class PreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val THEME_KEY = stringPreferencesKey("theme_option")
        private val FONT_KEY = stringPreferencesKey("font_option")
    }

    // Read preferences as a flow
    val preferencesFlow: Flow<UserSettings> = context.dataStore.data.map { preferences ->
        val themeName = preferences[THEME_KEY] ?: ThemeOption.SYSTEM.name
        val fontName = preferences[FONT_KEY] ?: FontOption.DEFAULT.name

        UserSettings(
            theme = ThemeOption.valueOf(themeName),
            font = FontOption.valueOf(fontName)
        )
    }

    // Save preferences
    suspend fun savePreferences(theme: ThemeOption, font: FontOption) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme.name
            preferences[FONT_KEY] = font.name
        }
    }
}
