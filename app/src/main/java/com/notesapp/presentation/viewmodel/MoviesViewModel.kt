package com.notesapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.remote.MovieResponse
import com.notesapp.domain.usecase.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase
) : ViewModel() {

    private val _movieState = MutableStateFlow<Result<MovieResponse>?>(null)
    val movieState: StateFlow<Result<MovieResponse>?> = _movieState

    fun fetchMovie(title: String) {
        viewModelScope.launch {
            getMovieUseCase(title).collect { result ->
                _movieState.value = result
            }
        }
    }
}
