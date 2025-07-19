package com.notesapp.domain.usecase

import com.notesapp.data.local.entity.ImageConfigEntity
import com.notesapp.domain.repository.ImdbConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImdbImageConfigUseCase @Inject constructor(
    private val repository: ImdbConfigRepository
) {
    suspend operator fun invoke(): Flow<ImageConfigEntity> {
        return repository.downloadConfig()
    }
}
