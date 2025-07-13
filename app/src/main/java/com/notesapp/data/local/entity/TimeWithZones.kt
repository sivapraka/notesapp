package com.notesapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CountryWithZones(
    @Embedded val country: TimeZoneEntity,

    @Relation(
        parentColumn = "isoCode",
        entityColumn = "countryCode"
    )
    val zones: List<ZoneEntity>
)