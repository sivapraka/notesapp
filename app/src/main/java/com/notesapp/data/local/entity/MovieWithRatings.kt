package com.notesapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation


data class MovieWithRatings(
    @Embedded val movie: Movies,
    @Relation(
        parentColumn = "imdbID",
        entityColumn = "movieId"
    )
    val ratings: List<Rating>
)