package com.notesapp.domain.repository

import androidx.paging.PagingData
import com.notesapp.data.local.entity.ImdbMovies
import kotlinx.coroutines.flow.Flow

interface ImdbMovieRepository {
     suspend  fun getMovie(language: String): Flow<PagingData<ImdbMovies>>
}
