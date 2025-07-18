package com.notesapp.data.model

import com.google.gson.annotations.SerializedName
import com.notesapp.data.local.entity.ImdbMovies

data class ImdbMovieResponse(
    @SerializedName("results") val results: List<ImdbMovies>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int,
)
