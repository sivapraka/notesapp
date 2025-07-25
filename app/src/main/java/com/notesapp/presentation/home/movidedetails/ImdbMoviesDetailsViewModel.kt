package com.notesapp.presentation.home.movidedetails

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.local.entity.ImdbMoviesDetails
import com.notesapp.data.local.entity.MoviesVideosResponse
import com.notesapp.domain.usecase.GetImdbMoviesDetailsUseCase
import com.notesapp.domain.usecase.GetImdbMoviesVideosUseCase
import com.notesapp.domain.usecase.RefreshImdbMoviesDetailsUseCase
import com.notesapp.util.ApiResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ImdbMoviesDetailsViewModel @Inject constructor(
  private val GetImdbMoviesVideosUseCase: GetImdbMoviesVideosUseCase,
    private val refreshImdbMoviesDetailsUseCase: RefreshImdbMoviesDetailsUseCase
) : ViewModel() {
    private var _movieDetails =
        MutableStateFlow<ApiResource<ImdbMoviesDetails?>>(ApiResource.Loading)
    var movieDetails: StateFlow<ApiResource<ImdbMoviesDetails?>> = _movieDetails
    private var _videos =
        MutableStateFlow<ApiResource<MoviesVideosResponse?>>(ApiResource.Loading)
    var videos: StateFlow<ApiResource<MoviesVideosResponse?>> = _videos


    fun movieDetails(movieId: Int) {
        viewModelScope.launch {
            refreshImdbMoviesDetailsUseCase(movieId).collect { result ->
                _movieDetails.value = result
            }

        }
    }

    fun loadVideos(movieId: Int) {
        viewModelScope.launch {
            GetImdbMoviesVideosUseCase(movieId).collect{
                data->
                _videos.value=data
            }
        }
    }
}