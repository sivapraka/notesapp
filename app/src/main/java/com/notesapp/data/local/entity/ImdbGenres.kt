package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "imdb_genre")
data class ImdbGenres(
    @SerializedName("id") @PrimaryKey val id: Int,
    @SerializedName("name") val name: String
)