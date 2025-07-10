package com.notesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.notesapp.data.local.entity.MovieWithRatings
import com.notesapp.data.local.entity.Movies
import com.notesapp.data.local.entity.Rating


@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movies)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRatings(ratings: List<Rating>)

    @Transaction
    @Query("SELECT * FROM movies WHERE title = :id")
    suspend fun getMovieWithRatings(id: String): MovieWithRatings?

    @Transaction
    @Query("SELECT * FROM movies ")
    suspend fun getMovies(): List<MovieWithRatings>
}