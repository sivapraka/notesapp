package com.notesapp.features.login.presentation.event



sealed class LoginEvent {
    data class OnEmailChanged(val email: String) : LoginEvent()
    data class OnPasswordChanged(val password: String) : LoginEvent()
    object OnLoginClick : LoginEvent()
    object OnLogoutClick : LoginEvent()
}