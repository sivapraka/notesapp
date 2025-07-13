package com.notesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.notesapp.data.local.entity.TimeZoneEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeZoneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(timezone: List<TimeZoneEntity>)

    @Query("SELECT * FROM timezone order by iso_3166_1 asc")
    fun getTimeZone(): Flow<List<TimeZoneEntity>>

}