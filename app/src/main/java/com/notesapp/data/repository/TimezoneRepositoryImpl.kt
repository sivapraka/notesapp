package com.notesapp.data.repository

import com.notesapp.data.local.dao.TimeZoneDao
import com.notesapp.data.local.entity.TimeZoneEntity
import com.notesapp.data.remote.ImdbApi
import com.notesapp.domain.repository.TimezoneRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimezoneRepositoryImpl @Inject constructor(
    private val dao: TimeZoneDao,
    private val api: ImdbApi,
) : TimezoneRepository {

    override fun getTimezone(): Flow<List<TimeZoneEntity>> {
        return dao.getTimeZone()
    }

    override suspend fun refreshTimezones() {
        val remoteTimezones = api.getTimeZone()
        dao.insertAll(remoteTimezones)
    }
}