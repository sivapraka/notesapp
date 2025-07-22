package com.notesapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ProfileViewModel @Inject constructor(
    private val getUserSettings: GetUserSettingsUseCase,
    private val saveUserSettings: SaveUserSettingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState


    init {
        viewModelScope.launch {
            getUserSettings().collect { settings ->
                _uiState.value = _uiState.value.copy(
                    selectedTheme = settings.theme,
                    selectedFont = settings.font
                )
            }
        }
    }

    fun onThemeSelected(theme: ThemeOption) {
        _uiState.update { it.copy(selectedTheme = theme) }
        save()
    }

    fun onFontSelected(font: FontOption) {
        _uiState.update { it.copy(selectedFont = font) }
        save()
    }

    private fun save() {
        viewModelScope.launch {
            saveUserSettings(_uiState.value.selectedTheme, _uiState.value.selectedFont)
        }
    }
}