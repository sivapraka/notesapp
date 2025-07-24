package com.notesapp.data.local.dao

import androidx.room.*
import com.notesapp.data.local.entity.MoviesVideos
import com.notesapp.data.local.entity.MoviesVideosResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieVideosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(movie: List<MoviesVideos>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(movie: MoviesVideosResponse)

    @Transaction
    @Query("SELECT * FROM movies_videos WHERE id = :id")
     fun videoDetails(id: Int):  Flow<List<MoviesVideos?>>

    @Transaction
    @Query("SELECT * FROM videos_response WHERE id = :id")
    fun response(id: Int):  Flow<MoviesVideosResponse?>

    @Query("DELETE FROM movies_videos")
    suspend  fun clearMovies()
}