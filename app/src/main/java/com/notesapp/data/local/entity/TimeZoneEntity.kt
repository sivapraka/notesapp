package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "timezone")
data class TimeZoneEntity(
    @SerializedName("iso_3166_1") @PrimaryKey val isoCode: String, // e.g., "AU", "AT"
)