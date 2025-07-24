package com.notesapp.domain.repository

import com.notesapp.data.local.entity.ImdbMoviesDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
     suspend  fun downloadDetails(movieId:Int): Flow<ImdbMoviesDetails?>
     suspend fun  getMovieDetails(movieId:Int): Flow<ImdbMoviesDetails?>
}
