package com.notesapp.features.login.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.features.login.domain.repository.LoginRepository
import com.notesapp.features.login.domain.usecase.LoginUseCase
import com.notesapp.features.login.domain.usecase.LogoutUseCase
import com.notesapp.features.login.presentation.event.LoginEvent
import com.notesapp.features.login.presentation.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> {
                _uiState.update { it.copy(email = event.email) }
            }

            is LoginEvent.OnPasswordChanged -> {
                _uiState.update { it.copy(password = event.password) }
            }

            is LoginEvent.OnLoginClick -> {
                login()
            }

            is LoginEvent.OnLogoutClick -> {
                logout()
            }
        }
    }

    fun getSavedToken(): String? {
        return loginRepository.getSavedToken()
    }


    private fun logout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            logoutUseCase()
            _uiState.update {
                it.copy(
                    email = "",
                    password = "",
                    isLoggedIn = false,
                    token = null,
                    errorMessage = null
                )
            }
        }
    }

    private fun login() {
        val email = _uiState.value.email
        val password = _uiState.value.password
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val user = loginUseCase(email, password)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        token = user.token
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message ?: "Login failed")
                }
            }
        }
    }
}
