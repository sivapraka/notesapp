package com.notesapp.domain.usecase

import android.os.Build
import androidx.annotation.RequiresExtension
import com.notesapp.data.local.entity.LanguageEntity
import com.notesapp.domain.repository.LanguageRepository
import com.notesapp.util.ApiResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshLanguagesUseCase @Inject constructor(private val repository: LanguageRepository) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(): Flow<ApiResource<List<LanguageEntity>>> = flow {
        emit(ApiResource.Loading)
        repository.refreshLanguages()
        repository.getLanguage().collect { languages ->
            emit(ApiResource.Success(languages))
        }
    }
}