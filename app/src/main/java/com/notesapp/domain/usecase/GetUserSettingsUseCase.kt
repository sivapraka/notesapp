package com.notesapp.domain.usecase

import com.notesapp.data.model.UserSettings
import com.notesapp.domain.repository.UserSettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserSettingsUseCase @Inject constructor(
    private val repository: UserSettingsRepository
) {
    operator fun invoke(): Flow<UserSettings> = repository.userSettings
}