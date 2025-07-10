package com.notesapp.features.login.domain.usecase

import com.notesapp.features.login.domain.repository.LoginRepository

class LogoutUseCase(
    private val repository: LoginRepository
) {
    operator fun invoke() {
        repository.logout()
    }
}