package com.notesapp.features.login.data.model

data class LoginResponseDto(
    var token: String,
    var userEmail: String,
    val userMobile: String

)