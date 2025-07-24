package com.notesapp.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.notesapp.domain.usecase.GetImdbMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@SuppressLint("CheckResult")
@HiltViewModel
class ImdbMoviesViewModel @Inject constructor(
    private val getImdbMovieUseCase: GetImdbMovieUseCase
) : ViewModel() {
    val movies = getImdbMovieUseCase()
        .cachedIn(viewModelScope)

}