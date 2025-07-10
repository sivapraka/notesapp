package com.notesapp.data.repository

import com.notesapp.data.local.dao.MoviesDao
import com.notesapp.data.mapper.toMovie
import com.notesapp.data.mapper.toMovieEntity
import com.notesapp.data.mapper.toRatingEntity
import com.notesapp.data.remote.MovieResponse
import com.notesapp.data.remote.api.MoviesApi
import com.notesapp.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val moviesDao: MoviesDao,
    private val api: MoviesApi,
    private val apiKey: String
) : MovieRepository {
    override suspend fun getMovie(title: String): MovieResponse {
        val movieResponse = api.getMovieByTitle(title, apiKey)
        moviesDao.insertMovie(movieResponse.toMovieEntity())
        moviesDao.insertRatings(movieResponse.toRatingEntity())
        return moviesDao.getMovieWithRatings(title)?.toMovie() ?: movieResponse
    }
}