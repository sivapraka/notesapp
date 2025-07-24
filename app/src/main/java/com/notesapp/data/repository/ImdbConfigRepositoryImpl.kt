package com.notesapp.data.repository

import com.notesapp.data.local.dao.ImageConfigDao
import com.notesapp.data.local.dao.KeyConfigDao
import com.notesapp.data.local.entity.ImageConfigEntity
import com.notesapp.data.mapper.toChangeKey
import com.notesapp.data.remote.ImdbApi
import com.notesapp.domain.repository.ImdbConfigRepository
import kotlinx.coroutines.flow.*
import timber.log.Timber

class ImdbConfigRepositoryImpl(
    private val imageConfigDao: ImageConfigDao,
    private val keyConfigDao: KeyConfigDao,
    private val api: ImdbApi,
) : ImdbConfigRepository {
    override fun downloadConfig(): Flow<ImageConfigEntity?> = flow {
        val cachedConfig = imageConfigDao.getConfig().firstOrNull()
        if (cachedConfig != null) {
            emit(cachedConfig)
        } else {
            try {
                val response = api.configuration()
                imageConfigDao.insertImageConfig(response.images)
                keyConfigDao.insertKeyConfig(response.changeKeys.toChangeKey())
                emit(imageConfigDao.getConfig().firstOrNull())
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

    }


    override suspend fun configs(): Flow<ImageConfigEntity?> {
        return imageConfigDao.getConfig()
    }

}