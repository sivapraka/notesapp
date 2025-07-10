package com.notesapp.features.login.data.remote

import com.notesapp.features.login.data.model.LoginRequestDto
import com.notesapp.features.login.data.model.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {
    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto
}
