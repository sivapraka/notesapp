package com.notesapp.data.repository

import com.notesapp.data.local.dao.TimeZoneDao
import com.notesapp.data.local.entity.TimeZoneEntity
import com.notesapp.data.remote.ImdbApi
import com.notesapp.domain.repository.TimezoneRepository
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class TimezoneRepositoryImpl @Inject constructor(
    private val dao: TimeZoneDao,
    private val api: ImdbApi,
) : TimezoneRepository {

    override  fun getTimezone(): Flow<List<TimeZoneEntity>> = flow {
        val cachedConfig = dao.getTimeZone().firstOrNull()
        if (!cachedConfig.isNullOrEmpty()) {
            emit(cachedConfig)
        }else{
            try {
                val remoteTimezones = api.getTimeZone()
                dao.insertAll(remoteTimezones)
                emit(remoteTimezones)
            } catch (e: Exception) {
                emit(emptyList())
                Timber.e(e)
            }
        }
    }

    override suspend fun refreshTimezones() {
        val remoteTimezones = api.getTimeZone()
        dao.insertAll(remoteTimezones)
    }
}