package com.notesapp.data.repository

import com.notesapp.data.local.dao.NoteDao
import com.notesapp.data.local.entity.Note
import com.notesapp.data.mapper.toDomain
import com.notesapp.data.remote.ApiService
import com.notesapp.data.remote.NetworkUtils
import com.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val apiService: ApiService,
    private val networkUtil: NetworkUtils
) : NoteRepository {
    override suspend fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    override suspend fun insertNote(note: Note) {
        val isNetworkAvailable = networkUtil.isNetworkAvailable()
        if (isNetworkAvailable) {
            try {
                val response = apiService.uploadNote(note.toDomain()) // convert to DTO
                if (response.isSuccessful) {
                    noteDao.insertAll(note.copy(upload = "Y"))
                } else {
                    noteDao.insertAll(note.copy(upload = "N"))
                }
            } catch (e: Exception) {
                noteDao.insertAll(note.copy(upload = "N"))
            }
        } else {
            noteDao.insertAll(note.copy(upload = "N"))
        }
    }

    override suspend fun deleteNote(note: Note) {
        return noteDao.delete(note)
    }

    override suspend fun updateNote(note: Note) {
        return noteDao.update(note)
    }

    override suspend fun deleteAllNotes() {
        return noteDao.deleteAllNotes()
    }

    override suspend fun deleteNoteById(id: Int) {
        return noteDao.deleteNoteById(id)
    }

    override suspend fun getNoteById(id: Int): Flow<Note>? {
        return noteDao.getNoteById(id)
    }

    override suspend fun updateUploadStatus(noteId: Int, status: String) {
        return noteDao.updateUploadStatus(noteId, status)
    }
}

