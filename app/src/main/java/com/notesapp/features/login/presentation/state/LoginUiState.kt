package com.notesapp.features.login.presentation.state

import android.util.Patterns

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null,
    val token: String? = null
)
{
    val isLoginEnabled: Boolean
        get() = email.isNotBlank() && password.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}