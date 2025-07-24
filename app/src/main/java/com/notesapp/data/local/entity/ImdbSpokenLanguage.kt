package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "imdb_spoken_languages")
data class ImdbSpokenLanguage(
    @SerializedName("iso_639_1") @PrimaryKey val iso_639_1: String,
    @SerializedName("name") val name: String,
    @SerializedName("english_name") val english_name: String?="",
)