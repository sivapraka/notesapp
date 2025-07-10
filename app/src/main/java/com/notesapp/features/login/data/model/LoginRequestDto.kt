package com.notesapp.features.login.data.model

data class LoginRequestDto(
    val email: String?="",
    val password: String?="",
    val mobileno: String?=""
)