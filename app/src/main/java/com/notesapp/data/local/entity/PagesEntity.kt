package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pages")
data class PagesEntity(
    @SerializedName("id") @PrimaryKey val id: Int=0,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int,
    @SerializedName("label") var label: String = "imdb_movies"

)