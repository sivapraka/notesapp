package com.notesapp.domain.usecase

import com.notesapp.data.datasource.PreferenceManager
import com.notesapp.data.local.entity.MoviesVideosResponse
import com.notesapp.domain.repository.MovieVideosRepository
import com.notesapp.util.ApiResource
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject


class GetImdbMoviesVideosUseCase @Inject constructor(
    private val repository: MovieVideosRepository,
    private val preferenceManager: PreferenceManager
) {
    operator fun invoke(movieId: Int): Flow<ApiResource<MoviesVideosResponse?>> {
        return preferenceManager.selectedLanguage
            .flatMapLatest { language ->
                flow {
                    emit(ApiResource.Loading)
                    try {
                        val result = repository.downloadVideos(movieId, language)
                        emitAll(result.map { ApiResource.Success(it) })
                    } catch (e: Exception) {
                        Timber.e(e.message.toString())
                        emit(ApiResource.Error(e.message.toString()))
                    }
                }
            }
    }
}
