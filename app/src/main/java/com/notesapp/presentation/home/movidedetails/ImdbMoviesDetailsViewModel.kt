package com.notesapp.presentation.home.movidedetails

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.local.entity.ImdbMoviesDetails
import com.notesapp.domain.usecase.GetImdbMoviesDetailsUseCase
import com.notesapp.domain.usecase.RefreshImdbMoviesDetailsUseCase
import com.notesapp.util.ApiResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ImdbMoviesDetailsViewModel @Inject constructor(
    private val getImdbMoviesDetailsUseCase: GetImdbMoviesDetailsUseCase,
    private val refreshImdbMoviesDetailsUseCase: RefreshImdbMoviesDetailsUseCase
) : ViewModel() {
    private var _movieDetails =
        MutableStateFlow<ApiResource<ImdbMoviesDetails?>>(ApiResource.Loading)
    var movieDetails: StateFlow<ApiResource<ImdbMoviesDetails?>> = _movieDetails

    fun movieDetails(movieId: Int) {
        viewModelScope.launch {
            Log.e("TAG", "movieDetails: "+movieId)
            refreshImdbMoviesDetailsUseCase(movieId).collect { result ->
                Log.e("TAG", "movieDetails: "+"Referesh" )
                _movieDetails.value = result
            }

        }
    }

    fun RefreshMovieDetails(movieId: Int) {
        viewModelScope.launch {
            refreshImdbMoviesDetailsUseCase(movieId)
        }

    }
}