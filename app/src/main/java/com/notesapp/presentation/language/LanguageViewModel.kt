package com.notesapp.presentation.language

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.datasource.PreferenceManager
import com.notesapp.data.local.entity.LanguageEntity
import com.notesapp.domain.usecase.GetLanguageUseCase
import com.notesapp.domain.usecase.RefreshLanguagesUseCase
import com.notesapp.util.ApiResource
import com.notesapp.util.network.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getLanguagesUseCase: GetLanguageUseCase,
    private val refreshLanguagesUseCase: RefreshLanguagesUseCase,
    networkObserver: NetworkConnectivityObserver,
   private  val preferenceManager: PreferenceManager,
) : ViewModel() {
    private var _languages =
        MutableStateFlow<ApiResource<List<LanguageEntity>>>(ApiResource.Loading)
    var languages: StateFlow<ApiResource<List<LanguageEntity>>> = _languages
    private val _selectedLanguage = MutableStateFlow("English")
    val selectedLanguage: StateFlow<String> = _selectedLanguage
  /*  private var _country = MutableStateFlow(Locale.getDefault().displayCountry) // or "Bengaluru"
    var country: StateFlow<String> = _country*/
  val isOnline: StateFlow<Boolean> = networkObserver.networkStatus
      .map { it == NetworkConnectivityObserver.Status.Available }
      .stateIn(
          viewModelScope,
          SharingStarted.WhileSubscribed(5000),
          false
      )

    fun languages() {
        viewModelScope.launch {
            getLanguagesUseCase().collect { _languages.value = it }
            // Observe selected language code
            preferenceManager.selectedLanguage.collect { code ->
                val languageList = (_languages.value as? ApiResource.Success)?.data ?: emptyList()
                val match = languageList.find { it.iso_639_1 == code }
                _selectedLanguage.value = match?.english_name ?: "English"
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            refreshLanguagesUseCase()
        }
    }

    fun onCountrySelected(languageName: String) {
        viewModelScope.launch {
            preferenceManager.saveLanguage(languageName)
            val languageList = (_languages.value as? ApiResource.Success)?.data ?: emptyList()
            val match = languageList.find { it.iso_639_1 == languageName }
            _selectedLanguage.value = match?.english_name ?: "English"
        }
    }
}