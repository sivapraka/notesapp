package com.notesapp.domain.usecase

import com.notesapp.data.remote.MovieResponse
import com.notesapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetMovieUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(title: String): Flow<Result<MovieResponse>> = flow {
        try {
            val result = repository.getMovie(title)
            result.Title
            emit(Result.success(result))
        } catch (e: Exception) {
            println(e.printStackTrace().toString())
            emit(Result.failure(e))
        }
    }
}
