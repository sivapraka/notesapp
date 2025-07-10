package com.notesapp.domain.repository

import com.notesapp.data.remote.MovieResponse

interface MovieRepository {

    suspend fun getMovie(title: String): MovieResponse
}
