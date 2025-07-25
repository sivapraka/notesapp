package com.notesapp.domain.usecase

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
    operator fun invoke(movieId: Int): Flow<ApiResource<ImdbMoviesDetails?>> {
        return preferenceManager.selectedLanguage
            .flatMapLatest { language ->
                flow {
                    emit(ApiResource.Loading)
                    try {
                        val result = repository.downloadDetails(movieId, language)
                        emitAll(result.map { ApiResource.Success(it) })
                    } catch (e: Exception) {
                        Timber.e(e.message.toString())
                        emit(ApiResource.Error(e.message.toString()))
                    }
                }
            }
    }
}
