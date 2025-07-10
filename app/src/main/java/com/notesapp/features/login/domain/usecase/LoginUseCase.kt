package com.notesapp.features.login.domain.usecase

import com.notesapp.features.login.domain.model.User
import com.notesapp.features.login.domain.repository.LoginRepository

class LoginUseCase(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): User {
        return repository.login(email, password)
    }
}
