package com.notesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note order by date desc")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id IN (:noteIds)")
    fun loadAllByIds(noteIds: IntArray): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg notes: Note)

    @Delete
   suspend fun delete(note: Note)

    @Update
    suspend  fun update(note: Note)

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun deleteNoteById(id: Int)

    @Query("DELETE FROM note")
    fun deleteAllNotes()

    @Query("SELECT * FROM note WHERE id = :id")
    fun getNoteById(id: Int): Flow<Note>?

    @Query("UPDATE note SET upload = :status WHERE id = :noteId")
    suspend fun updateUploadStatus(noteId: Int, status: String)

}