package com.notesapp.data.remote.api

import com.notesapp.data.remote.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("/")
    suspend fun getMovieByTitle(
        @Query("t") title: String,
        @Query("apikey") apiKey: String
    ): MovieDto
}