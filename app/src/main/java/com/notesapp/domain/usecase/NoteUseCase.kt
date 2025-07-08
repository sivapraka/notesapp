package com.notesapp.domain.usecase

data class NoteUseCase(
    val addNotesUseCase: AddNotesUseCase,
    val deleteNotesUseCase: DeleteNotesUseCase,
    val getNotesUseCase: GetNotesUseCase
)