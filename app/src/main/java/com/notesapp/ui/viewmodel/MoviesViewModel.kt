package com.notesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.domain.model.Movie
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

    private val _movieState = MutableStateFlow<Result<Movie>?>(null)
    val movieState: StateFlow<Result<Movie>?> = _movieState

    fun fetchMovie(title: String) {
        viewModelScope.launch {
            getMovieUseCase(title).collect { result ->
                _movieState.value = result
            }
        }
    }
}
