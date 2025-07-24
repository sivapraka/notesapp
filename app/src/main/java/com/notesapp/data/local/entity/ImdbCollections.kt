package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "imdb_collections")
data class ImdbCollections(
    @SerializedName("id") @PrimaryKey val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("poster_path") val poster_path: String?="",
    @SerializedName("backdrop_path") val backdrop_path: String?="",
)