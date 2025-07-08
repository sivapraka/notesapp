package com.notesapp.domain.usecase

import com.notesapp.domain.repository.NoteRepository

class DeleteNotesUseCase(private val  repository: NoteRepository) {
    suspend operator fun invoke(note: Int) {
        repository.deleteNoteById(note)
    }

}