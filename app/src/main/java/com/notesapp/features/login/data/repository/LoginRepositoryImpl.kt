package com.notesapp.features.login.data.repository


import com.notesapp.features.login.data.local.EncryptedPrefs
import com.notesapp.features.login.data.model.LoginRequestDto
import com.notesapp.features.login.data.remote.AuthService
import com.notesapp.features.login.domain.model.User
import com.notesapp.features.login.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val api: AuthService,
    private val securePrefs: EncryptedPrefs
) : LoginRepository {
    override suspend fun login(email: String, password: String): User {
        var response = api.login(LoginRequestDto(email, password))
        // Encrypt and store the token securely
        response.token="123456"
        response.userEmail="siva@gmail.com"

        securePrefs.saveToken(response.token)
        return User(email = response.userEmail, token = response.token)
    }

    override fun getSavedToken(): String? = securePrefs.getToken()

    override fun logout() = securePrefs.clear()
}
