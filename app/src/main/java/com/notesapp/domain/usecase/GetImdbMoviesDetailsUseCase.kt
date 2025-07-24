package com.notesapp.domain.usecase

import com.notesapp.data.local.entity.ImdbMoviesDetails
import com.notesapp.domain.repository.MovieDetailsRepository
import com.notesapp.util.ApiResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetImdbMoviesDetailsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {
    operator fun invoke(movieId: Int): Flow<ApiResource<ImdbMoviesDetails?>> = flow {
        try {
            emit(ApiResource.Loading)
            repository.getMovieDetails(movieId).collect { it ->
                emit(ApiResource.Success(it))
            }
        } catch (e: Exception) {
            emit(ApiResource.Error(e.message.toString()))
        }

    }
}
