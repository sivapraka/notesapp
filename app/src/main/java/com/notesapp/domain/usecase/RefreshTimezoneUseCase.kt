package com.notesapp.domain.usecase

import com.notesapp.data.local.entity.TimeZoneEntity
import com.notesapp.domain.repository.TimezoneRepository
import com.notesapp.util.ApiResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshTimezoneUseCase @Inject constructor(private val repository: TimezoneRepository) {
    operator fun invoke(): Flow<ApiResource<List<TimeZoneEntity>>> = flow {
        emit(ApiResource.Loading)
        repository.getTimezone().collect { languages ->
            emit(ApiResource.Success(languages))
        }
    }
}