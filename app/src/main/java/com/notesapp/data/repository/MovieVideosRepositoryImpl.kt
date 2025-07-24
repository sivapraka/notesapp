package com.notesapp.data.repository

import com.notesapp.data.local.dao.MovieVideosDao
import com.notesapp.data.local.entity.MoviesVideosResponse
import com.notesapp.data.remote.ImdbApi
import com.notesapp.domain.repository.MovieVideosRepository
import kotlinx.coroutines.flow.Flow

class MovieVideosRepositoryImpl(private  val videosDao:MovieVideosDao, private  val api:ImdbApi):MovieVideosRepository {
    override suspend fun downloadVideos(id: Int): Flow<MoviesVideosResponse?> {
        val videos=api.videos(1087192)
        videosDao.insertVideo(videos)
       return videosDao.response(id)
    }
}