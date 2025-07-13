package com.notesapp.presentation.language

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.local.entity.LanguageEntity
import com.notesapp.domain.usecase.GetLanguageUseCase
import com.notesapp.domain.usecase.RefreshLanguagesUseCase
import com.notesapp.util.ApiResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getLanguagesUseCase: GetLanguageUseCase,
    private val refreshLanguagesUseCase: RefreshLanguagesUseCase
) : ViewModel() {
    private var _languages =
        MutableStateFlow<ApiResource<List<LanguageEntity>>>(ApiResource.Loading)
    var languages: StateFlow<ApiResource<List<LanguageEntity>>> = _languages
  /*  private var _country = MutableStateFlow(Locale.getDefault().displayCountry) // or "Bengaluru"
    var country: StateFlow<String> = _country*/

    init {
        Log.e("TAG", ": "+"API Trigger")
        viewModelScope.launch {
            Log.e("TAG", ": "+"API Trigger")
            getLanguagesUseCase().collect { _languages.value = it }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            refreshLanguagesUseCase()
        }
    }

    fun onCountrySelected(c: String) {
      //  _country.value = c
    }
}