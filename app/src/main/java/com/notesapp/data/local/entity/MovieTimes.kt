package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_time")
data class MovieTimes(
    @PrimaryKey val imdbID: Int=0,
    val title: String="",
    val time: String
)