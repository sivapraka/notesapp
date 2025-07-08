package com.notesapp.domain.usecase

import com.notesapp.domain.model.Movie
import com.notesapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetMovieUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(title: String): Flow<Result<Movie>> = flow {
        try {
            emit(Result.success(repository.getMovie(title)))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
