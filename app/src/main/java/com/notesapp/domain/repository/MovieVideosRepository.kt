package com.notesapp.domain.repository

import com.notesapp.data.local.entity.MoviesVideosResponse
import kotlinx.coroutines.flow.Flow

interface MovieVideosRepository {
    suspend fun downloadVideos(id: Int,language: String): Flow<MoviesVideosResponse?>
}