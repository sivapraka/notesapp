package com.notesapp.domain.usecase

import com.notesapp.data.local.entity.Note

import com.notesapp.domain.repository.NoteRepository

class AddNotesUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        repository.insertNote(note)
    }

}