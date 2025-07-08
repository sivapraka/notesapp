package com.notesapp.domain.repository

import com.notesapp.data.local.Note
import com.notesapp.data.remote.ApiService
import com.notesapp.data.remote.NetworkUtils
import kotlinx.coroutines.flow.Flow

interface NoteRepository{
    suspend fun getNotes(): Flow<List<Note>>
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteAllNotes()
    suspend fun deleteNoteById(id: Int)
    suspend fun getNoteById(id: Int): Flow<Note>?
    suspend fun updateUploadStatus(noteId: Int, status: String)
}