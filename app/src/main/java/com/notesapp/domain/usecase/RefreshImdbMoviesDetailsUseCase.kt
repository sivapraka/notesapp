package com.notesapp.domain.usecase

import android.util.Log
import com.notesapp.data.datasource.PreferenceManager
import com.notesapp.data.local.entity.ImdbMoviesDetails
import com.notesapp.domain.repository.MovieDetailsRepository
import com.notesapp.util.ApiResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject


class RefreshImdbMoviesDetailsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository,
    private val preferenceManager: PreferenceManager
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(movieId: Int): Flow<ApiResource<ImdbMoviesDetails?>> =
        flow {
            try {
                emit(ApiResource.Loading)
                preferenceManager.selectedLanguage.flatMapLatest { language ->
                    Timber.tag("MoviesDetailsUseCase").e("language:$language")
                    Log.e("MoviesDetailsUseCase", "invoke: $language" )
                    repository.downloadDetails(movieId, language)
                }
            } catch (e: Exception) {
                Timber.e(e.message.toString())
                emit(ApiResource.Error(e.message.toString()))
            }
        }
}
