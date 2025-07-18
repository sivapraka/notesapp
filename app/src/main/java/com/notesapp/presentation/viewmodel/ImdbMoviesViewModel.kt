package com.notesapp.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.notesapp.data.local.entity.ImdbMovies
import com.notesapp.domain.usecase.GetImdbMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


@SuppressLint("CheckResult")
@HiltViewModel
class ImdbMoviesViewModel @Inject constructor(
    private val getImdbMovieUseCase: GetImdbMovieUseCase
) : ViewModel() {
    private val _language = MutableStateFlow("ta")
    val language = _language.asStateFlow()
    var movies: Flow<PagingData<ImdbMovies>> = _language.flatMapLatest {lang->getImdbMovieUseCase(lang)  }.cachedIn(viewModelScope)

    fun setLanguage(newLang: String) {
        _language.value = newLang
    }

}