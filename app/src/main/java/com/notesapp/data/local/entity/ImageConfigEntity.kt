package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "images_config")
data class ImageConfigEntity(
    @SerializedName("id") @PrimaryKey(autoGenerate = true) val id: Int=0,
    @SerializedName("base_url") val baseUrl: String,
    @SerializedName("secure_base_url") val secureBaseUrl: String,
    @SerializedName("backdrop_sizes") val backdropSizes: List<String>,
    @SerializedName("logo_sizes") val logoSizes: List<String>,
    @SerializedName("poster_sizes") val posterSizes: List<String>,
    @SerializedName("profile_sizes") val profileSizes: List<String>,
    @SerializedName("still_sizes") val stillSizes: List<String>
)