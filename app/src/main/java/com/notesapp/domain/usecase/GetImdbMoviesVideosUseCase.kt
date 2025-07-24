package com.notesapp.domain.usecase

import com.notesapp.data.local.entity.MoviesVideosResponse
import com.notesapp.domain.repository.MovieVideosRepository
import com.notesapp.util.ApiResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject


class GetImdbMoviesVideosUseCase @Inject constructor(
    private val repository: MovieVideosRepository
) {
    operator fun invoke(movieId: Int): Flow<ApiResource<MoviesVideosResponse?>> =
        flow {
            try {
                emit(ApiResource.Loading)
                repository.downloadVideos(movieId).collect {
                    emit(ApiResource.Success(it))
                }
            } catch (e: Exception) {
                Timber.e(e.message.toString())
                emit(ApiResource.Error(e.message.toString()))
            }
        }
}
