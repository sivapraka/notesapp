package com.notesapp.domain.repository

import com.notesapp.data.local.entity.LanguageEntity
import com.notesapp.domain.model.RequestTokenResponse
import kotlinx.coroutines.flow.Flow


interface LanguageRepository {
    suspend fun getRequestToken(): RequestTokenResponse
    fun getLanguage(): Flow<List<LanguageEntity>>
    suspend fun refreshLanguages()
}
