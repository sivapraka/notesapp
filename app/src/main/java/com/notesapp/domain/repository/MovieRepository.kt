package com.notesapp.domain.repository

import com.notesapp.domain.model.Movie

interface MovieRepository {

    suspend fun getMovie(title: String): Movie
}
