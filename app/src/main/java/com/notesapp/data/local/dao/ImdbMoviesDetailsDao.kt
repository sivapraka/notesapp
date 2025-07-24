package com.notesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.notesapp.data.local.entity.ImdbMoviesDetails
import kotlinx.coroutines.flow.Flow


@Dao
interface ImdbMoviesDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetails(movie: ImdbMoviesDetails)

    @Transaction
    @Query("SELECT * FROM imdb_movies_details WHERE id = :id")
     fun getMovieDetails(id: Int):  Flow<ImdbMoviesDetails?>

    @Query("DELETE FROM imdb_movies_details")
    suspend  fun clearMovies()
}