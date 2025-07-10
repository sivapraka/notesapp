package com.notesapp.features.login.domain.repository

import com.notesapp.features.login.domain.model.User


interface LoginRepository {
    suspend fun login(email: String, password: String): User
    fun getSavedToken(): String?
    fun logout()
}
