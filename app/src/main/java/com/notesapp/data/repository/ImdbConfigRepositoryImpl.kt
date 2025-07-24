package com.notesapp.data.repository

import com.notesapp.data.local.dao.ImageConfigDao
import com.notesapp.data.local.dao.KeyConfigDao
import com.notesapp.data.local.entity.ImageConfigEntity
import com.notesapp.data.mapper.toChangeKey
import com.notesapp.data.remote.ImdbApi
import com.notesapp.domain.repository.ImdbConfigRepository
import kotlinx.coroutines.flow.Flow

class ImdbConfigRepositoryImpl(
    private val imageConfigDao: ImageConfigDao,
    private val keyConfigDao: KeyConfigDao,
    private val api: ImdbApi,
) : ImdbConfigRepository {
    override suspend fun downloadConfig() : Flow<ImageConfigEntity>{
            val response = api.configuration()
            imageConfigDao.insertImageConfig(response.images)
            keyConfigDao.insertKeyConfig(response.changeKeys.toChangeKey())
            return imageConfigDao.getConfig()
    }

    override suspend fun configs(): Flow<ImageConfigEntity> {
        return imageConfigDao.getConfig()
    }

}