package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "zones",
    foreignKeys = [
        ForeignKey(
            entity = TimeZoneEntity::class,
            parentColumns = ["isoCode"],
            childColumns = ["countryCode"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("countryCode")]
)
data class ZoneEntity(
    @PrimaryKey val zoneId: String,         // e.g., "Australia/Sydney"
    val countryCode: String                 // FK to CountryEntity
)
