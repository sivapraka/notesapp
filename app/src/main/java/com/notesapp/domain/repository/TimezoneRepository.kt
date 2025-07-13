package com.notesapp.domain.repository

import com.notesapp.data.local.entity.TimeZoneEntity
import kotlinx.coroutines.flow.Flow

interface TimezoneRepository {

    fun getTimezone(): Flow<List<TimeZoneEntity>>

    suspend fun refreshTimezones()

}