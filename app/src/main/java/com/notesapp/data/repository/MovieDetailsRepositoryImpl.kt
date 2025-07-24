package com.notesapp.data.repository

import android.util.Log
import com.notesapp.data.local.dao.ImdbMoviesDetailsDao
import com.notesapp.data.local.entity.ImdbMoviesDetails
import com.notesapp.data.remote.ImdbApi
import com.notesapp.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow

class MovieDetailsRepositoryImpl(
    private val moviesDetailsDao: ImdbMoviesDetailsDao,
    private val api: ImdbApi,
) : MovieDetailsRepository {

    override suspend fun downloadDetails(
        movieid: Int,
    ): Flow<ImdbMoviesDetails?> {
        val response = api.movieDetails(movieid, "ta")
        moviesDetailsDao.insertDetails(response)
        return moviesDetailsDao.getMovieDetails(movieid)
    }

    override suspend fun getMovieDetails(movieid: Int): Flow<ImdbMoviesDetails?> {
        return moviesDetailsDao.getMovieDetails(movieid)
    }

}