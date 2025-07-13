package com.notesapp.data.remote

import com.notesapp.data.local.entity.LanguageEntity
import com.notesapp.data.local.entity.TimeZoneEntity
import com.notesapp.domain.model.RequestTokenResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface ImdbApi {
    @POST("auth/request_token")
    suspend fun getRequestToken(): RequestTokenResponse

    @GET("configuration/languages")
    suspend fun getLanguages(): List<LanguageEntity>

    @GET("configuration/timezones")
    suspend fun getTimeZone(): List<TimeZoneEntity>

    ///discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc
}