package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "rating",
    foreignKeys = [
        ForeignKey(
            entity = Movies::class,
            parentColumns = ["imdbID"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["movieId"])]
)
data class Rating(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val source: String,
    val value: String,
    val movieId: String // Foreign Key to Movies
)