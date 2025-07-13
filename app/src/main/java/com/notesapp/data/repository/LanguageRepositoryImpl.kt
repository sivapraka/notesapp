package com.notesapp.data.repository

import android.util.Log
import com.notesapp.data.local.dao.LanguageDao
import com.notesapp.data.local.entity.LanguageEntity
import com.notesapp.data.remote.ImdbApi
import com.notesapp.domain.model.RequestTokenResponse
import com.notesapp.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val api: ImdbApi,
    val dao: LanguageDao
) : LanguageRepository {
    override suspend fun getRequestToken(): RequestTokenResponse {
        return api.getRequestToken()
    }

    override  fun getLanguage(): Flow<List<LanguageEntity>> {
        return dao.getAllLanguages()
    }


    override suspend fun refreshLanguages() {
        try {
            val remoteLanguages = api.getLanguages()
            val entities = remoteLanguages.map {
                LanguageEntity(it.iso_639_1, it.english_name, it.name)
            }
            dao.insertAll(entities)
        }catch (e: Exception){
            Log.e("TAG", "refreshLanguages: "+e.printStackTrace() )
        }

    }


}