package com.notesapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation


data class MovieWithGenre(
    @Embedded val movie: ImdbMovies,
    @Relation(parentColumn = "id", entityColumn = "genre_ids")
    val genre_ids: List<MoviesGenreId>
)