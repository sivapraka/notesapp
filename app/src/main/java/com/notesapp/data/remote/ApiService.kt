package com.notesapp.data.remote

import com.notesapp.domain.model.Notes
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/notes")
    suspend fun uploadNote(@Body note: Notes): Response

}