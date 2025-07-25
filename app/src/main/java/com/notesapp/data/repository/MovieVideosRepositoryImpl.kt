package com.notesapp.data.repository

import com.notesapp.data.local.dao.MovieVideosDao
import com.notesapp.data.local.entity.MoviesVideosResponse
import com.notesapp.data.remote.ImdbApi
import com.notesapp.domain.repository.MovieVideosRepository
import kotlinx.coroutines.flow.*

class MovieVideosRepositoryImpl(private  val videosDao:MovieVideosDao, private  val api:ImdbApi):MovieVideosRepository {
    override suspend fun downloadVideos(id: Int,language: String): Flow<MoviesVideosResponse?> = flow {
        val cacheDetails = videosDao.response(id).firstOrNull()
        if (cacheDetails != null) {
            emit(cacheDetails)
        } else {
            val videos=api.videos(id,language)
            videosDao.insertVideo(videos)
            emit(videosDao.response(id).firstOrNull())
        }
    }
}