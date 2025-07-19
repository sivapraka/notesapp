package com.notesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.notesapp.data.local.entity.ImageConfigEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ImageConfigDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImageConfig(movie: ImageConfigEntity)

    @Transaction
    @Query("SELECT * FROM images_config")
    fun getConfig(): Flow<ImageConfigEntity>

    @Query("DELETE FROM images_config")
    suspend fun clearConfig()
}