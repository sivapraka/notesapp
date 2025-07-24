package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "imdb_movies_details")
data class ImdbMoviesDetails(
    @SerializedName("id") @PrimaryKey val id: String,
    @SerializedName("vote_count") val vote_count: Int,
    @SerializedName("vote_average") val vote_average: Float,
    @SerializedName("video") val video: Boolean,
    @SerializedName("title") val title: String? = "",
    @SerializedName("release_date") val release_date: String,
    @SerializedName("poster_path") val poster_path: String? = "",
    @SerializedName("popularity") val popularity: Float? = 0.0f,
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("original_title") val original_title: String? = "",
    @SerializedName("original_language") val original_language: String? = "",
    @SerializedName("backdrop_path") val backdrop_path: String? = "",
    @SerializedName("adult") val adult: Boolean? = false,
    @SerializedName("genres") val genres: List<ImdbGenres>,
    @SerializedName("belongs_to_collection") val belongs_to_collection: ImdbCollections?,
    @SerializedName("budget") val budget: Int,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("imdb_id") val imdb_id: String,
    @SerializedName("revenue") val revenue: String? = "",
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("status") val status: String? = "",
    @SerializedName("tagline") val tagline: String? = "",
    @SerializedName("origin_country") val origin_country: List<String>?,
    @SerializedName("production_companies") val production_companies: List<ImdbProductionCompanies>,
    @SerializedName("production_countries") val production_countries: List<ImdbProductionCountries>,
    @SerializedName("spoken_languages") val spoken_languages: List<ImdbSpokenLanguage>,
)