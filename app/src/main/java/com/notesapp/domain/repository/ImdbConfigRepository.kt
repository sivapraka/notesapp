package com.notesapp.domain.repository

import com.notesapp.data.local.entity.ImageConfigEntity
import kotlinx.coroutines.flow.Flow

interface ImdbConfigRepository {
     suspend  fun downloadConfig(): Flow<ImageConfigEntity>
     suspend  fun configs(): Flow<ImageConfigEntity>
}
