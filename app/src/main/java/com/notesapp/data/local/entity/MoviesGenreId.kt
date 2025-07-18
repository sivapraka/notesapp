package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "genre_ids",
    foreignKeys = [
        ForeignKey(
            entity = ImdbMovies::class,
            parentColumns = ["id"],
            childColumns = ["genre_ids"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["genre_ids"])]
)
data class MoviesGenreId(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val genre_ids: String // Foreign Key to Movies
)