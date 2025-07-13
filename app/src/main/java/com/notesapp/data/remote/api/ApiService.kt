package com.notesapp.data.remote.api

import com.notesapp.domain.model.Notes
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/notes")
    suspend fun uploadNote(@Body note: Notes): Response

}