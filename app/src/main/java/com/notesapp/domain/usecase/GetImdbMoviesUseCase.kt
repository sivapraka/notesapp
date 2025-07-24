package com.notesapp.domain.usecase

import androidx.paging.PagingData
import com.notesapp.data.datasource.PreferenceManager
import com.notesapp.data.local.entity.ImdbMovies
import com.notesapp.domain.repository.ImdbMovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject


class GetImdbMovieUseCase @Inject constructor (
    private val repository: ImdbMovieRepository,
    private val preferenceManager: PreferenceManager,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
     operator fun invoke(): Flow<PagingData<ImdbMovies>> {
        return preferenceManager.selectedLanguage
            .flatMapLatest { language ->
                Timber.tag("GetImdbMovieUseCase").e("language $language")
                repository.getMovie(language)
            }
    }
}
