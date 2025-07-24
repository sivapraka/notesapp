package com.notesapp.domain.usecase

import android.util.Log
import com.notesapp.data.local.entity.ImdbMoviesDetails
import com.notesapp.domain.repository.MovieDetailsRepository
import com.notesapp.util.ApiResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RefreshImdbMoviesDetailsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {
    operator fun invoke(movieId: Int): Flow<ApiResource<ImdbMoviesDetails?>> =
        flow {
            try {
                emit(ApiResource.Loading)
                repository.downloadDetails(movieId)
                repository.getMovieDetails(movieId).collect { it ->
                    emit(ApiResource.Success(it))
                }
            } catch (e: Exception) {
                Log.e("RefreshImdbMoviesDetailsUseCase", "invoke: "+e.localizedMessage )
                emit(ApiResource.Error(e.message.toString()))
            }
        }
}
