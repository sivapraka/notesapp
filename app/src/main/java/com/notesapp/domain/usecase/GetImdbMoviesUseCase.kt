package com.notesapp.domain.usecase

import androidx.paging.PagingData
import com.notesapp.data.local.entity.ImdbMovies
import com.notesapp.domain.repository.ImdbMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetImdbMovieUseCase @Inject constructor (
    private val repository: ImdbMovieRepository
) {
    suspend operator fun invoke(language: String): Flow<PagingData<ImdbMovies>> {
        return repository.getMovie(language)
    }
}
