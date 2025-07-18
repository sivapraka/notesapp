package com.notesapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.notesapp.data.local.entity.ImdbMovies
import com.notesapp.data.local.entity.MovieWithGenre
import com.notesapp.data.local.entity.MoviesGenreId


@Dao
interface ImdbMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: List<ImdbMovies>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(ratings: List<MoviesGenreId>)

    @Transaction
    @Query("SELECT * FROM imdb_movies WHERE title = :id")
    suspend fun getMovieWithGenre(id: String): MovieWithGenre?

    @Transaction
    @Query("SELECT * FROM imdb_movies ORDER BY popularity DESC")
    fun getMovies(): PagingSource<Int, ImdbMovies>

    @Query("DELETE FROM imdb_movies")
    suspend  fun clearMovies()
}