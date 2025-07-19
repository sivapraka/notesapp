package com.notesapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.notesapp.data.local.entity.ChangeKey


@Dao
interface KeyConfigDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeyConfig(movie: List<ChangeKey>)

    @Transaction
    @Query("SELECT * FROM change_keys ")
    fun getKeyConfig(): PagingSource<Int, ChangeKey>

    @Query("DELETE FROM change_keys")
    suspend fun clearKeyConfig()
}