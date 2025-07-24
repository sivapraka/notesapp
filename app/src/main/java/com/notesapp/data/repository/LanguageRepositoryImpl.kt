package com.notesapp.data.repository

import com.notesapp.data.local.dao.LanguageDao
import com.notesapp.data.local.entity.LanguageEntity
import com.notesapp.data.remote.ImdbApi
import com.notesapp.domain.model.RequestTokenResponse
import com.notesapp.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val api: ImdbApi,
    val dao: LanguageDao
) : LanguageRepository {
    override suspend fun getRequestToken(): RequestTokenResponse {
        return api.getRequestToken()
    }

    override fun getLanguage(): Flow<List<LanguageEntity>> = flow {
        val cacheLanguage = dao.getAllLanguages().firstOrNull()
        if (!cacheLanguage.isNullOrEmpty()) {
            emit(cacheLanguage)
        } else {
            try {
                val remoteLanguages = api.getLanguages()
                dao.insertAll(remoteLanguages)
                emit(remoteLanguages)
            } catch (e: Exception) {
                Timber.e(e, "Failed to fetch languages from API")
                emit(emptyList())
            }
        }

    }


    override suspend fun refreshLanguages() {
        try {
            val remoteLanguages = api.getLanguages()
            dao.insertAll(remoteLanguages)
        } catch (e: Exception) {
            Timber.e(e)
        }

    }


}