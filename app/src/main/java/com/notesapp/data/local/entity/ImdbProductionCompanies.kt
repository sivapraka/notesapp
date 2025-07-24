package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "imdb_production_company")
data class ImdbProductionCompanies(
    @SerializedName("id") @PrimaryKey val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo_path") val logo_path: String? = "",
    @SerializedName("origin_country") val origin_country: String? = "",
)
