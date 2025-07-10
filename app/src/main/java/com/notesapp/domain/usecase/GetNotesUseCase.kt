package com.notesapp.domain.usecase

import com.notesapp.data.local.entity.Note
import com.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(): Flow<List<Note>> {
        return repository.getNotes()
    }
}