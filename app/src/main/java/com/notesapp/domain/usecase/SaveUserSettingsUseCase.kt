package com.notesapp.domain.usecase

import com.notesapp.domain.repository.UserSettingsRepository
import com.notesapp.presentation.profile.FontOption
import com.notesapp.presentation.profile.ThemeOption
import javax.inject.Inject

class SaveUserSettingsUseCase @Inject constructor(
    private val repository: UserSettingsRepository
) {
    suspend operator fun invoke(theme: ThemeOption, font: FontOption) {
        repository.saveSettings(theme, font)
    }
}