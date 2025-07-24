package com.notesapp.data.remote

import com.notesapp.data.local.entity.ImdbMoviesDetails
import com.notesapp.data.local.entity.LanguageEntity
import com.notesapp.data.local.entity.MoviesVideosResponse
import com.notesapp.data.local.entity.TimeZoneEntity
import com.notesapp.data.mapper.ConfigResponse
import com.notesapp.data.model.ImdbMovieResponse
import com.notesapp.domain.model.RequestTokenResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ImdbApi {
    @POST("auth/request_token")
    suspend fun getRequestToken(): RequestTokenResponse

    @GET("configuration/languages")
    suspend fun getLanguages(): List<LanguageEntity>

    @GET("configuration/timezones")
    suspend fun getTimeZone(): List<TimeZoneEntity>

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("language") language: String = "ta",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "popularity.desc",
    ): ImdbMovieResponse

    @GET("configuration")
    suspend fun configuration(): ConfigResponse


    @GET("movie/{movie_id}")
    fun movieDetails(
        @Path("movie_id") id: Int,
        @Query("language") language: String = "ta",
    ): ImdbMoviesDetails

    @GET("movie/{movie_id}/videos")
    suspend fun videos(
        @Path("movie_id") id: Int,
        @Query("language") language: String = "en",
    ): MoviesVideosResponse


}