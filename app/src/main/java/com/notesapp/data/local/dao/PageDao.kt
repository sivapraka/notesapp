package com.notesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.notesapp.data.local.entity.PagesEntity

@Dao
interface PageDao {
    @Query("SELECT * FROM pages WHERE id = 0")
    suspend fun getKeys(): PagesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: PagesEntity)


    @Query("SELECT * FROM pages WHERE label = :label")
    suspend fun getRemoteKey(label: String): PagesEntity?

    @Query("DELETE FROM pages")
    suspend fun clearKeys()

    @Query("DELETE FROM pages  WHERE label = :label")
    suspend fun clearKeys(label: String)
}