package com.notesapp.domain.repository

import com.notesapp.data.local.entity.ImdbMoviesDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    fun downloadDetails(movieId: Int, language: String): Flow<ImdbMoviesDetails?>
    fun getMovieDetails(movieId: Int): Flow<ImdbMoviesDetails?>
}
