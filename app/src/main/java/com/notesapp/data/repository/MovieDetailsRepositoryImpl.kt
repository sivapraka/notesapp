package com.notesapp.data.repository

import com.notesapp.data.local.dao.ImdbMoviesDetailsDao
import com.notesapp.data.local.entity.ImdbMoviesDetails
import com.notesapp.data.remote.ImdbApi
import com.notesapp.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.*

class MovieDetailsRepositoryImpl(
    private val moviesDetailsDao: ImdbMoviesDetailsDao,
    private val api: ImdbApi,
) : MovieDetailsRepository {
    override fun downloadDetails(movieId: Int, language: String): Flow<ImdbMoviesDetails?> = flow {
        val cacheDetails = moviesDetailsDao.getMovieDetails(movieId).firstOrNull()
        if (cacheDetails != null) {
            emit(cacheDetails)
        } else {
            val response = api.movieDetails(movieId, language)
            moviesDetailsDao.insertDetails(response)
            emit(moviesDetailsDao.getMovieDetails(movieId).firstOrNull())
        }
    }


    override fun getMovieDetails(movieId: Int): Flow<ImdbMoviesDetails?> = flow {
        emit(moviesDetailsDao.getMovieDetails(movieId).firstOrNull())
    }

}