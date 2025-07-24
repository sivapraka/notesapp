package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "imdb_production_countries")
data class ImdbProductionCountries(
    @SerializedName("iso_3166_1") @PrimaryKey val iso_3166_1: String,
    @SerializedName("name") val name: String,
)
