package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "imdb_movies")
data class ImdbMovies(
    @SerializedName("id") @PrimaryKey val id: String,
    @SerializedName("vote_count") val vote_count: Int,
    @SerializedName("vote_average") val vote_average: Float,
    @SerializedName("video") val video: Boolean,
    @SerializedName("title") val title: String?="",
    @SerializedName("release_date") val release_date: String,
    @SerializedName("poster_path") val poster_path: String?="",
    @SerializedName("popularity") val popularity: Float?=0.0f,
    @SerializedName("overview") val overview: String?="",
    @SerializedName("original_title") val original_title: String?="",
    @SerializedName("original_language") val original_language: String?="",
    @SerializedName("backdrop_path") val backdrop_path: String?="",
    @SerializedName("adult") val adult: Boolean?=false,
    @SerializedName("genre_ids") val genre_ids: List<Int>
)