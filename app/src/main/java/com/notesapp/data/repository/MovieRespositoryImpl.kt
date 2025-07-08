package com.notesapp.data.repository

import com.notesapp.data.mapper.toDomain
import com.notesapp.data.remote.api.MoviesApi
import com.notesapp.domain.model.Movie
import com.notesapp.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: MoviesApi,
    private val apiKey: String
) : MovieRepository {

    override suspend fun getMovie(title: String): Movie {
        return api.getMovieByTitle(title, apiKey).toDomain()
    }
}