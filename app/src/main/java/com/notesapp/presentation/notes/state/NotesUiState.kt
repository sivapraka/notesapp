package com.notesapp.presentation.notes.state

import com.notesapp.domain.model.NotesDataSource

sealed class NotesUiState {
    object Loading : NotesUiState()
    object Adding : NotesUiState()
    data class Success(val notes: List<NotesDataSource>) : NotesUiState()
    data class Error(val message: String) : NotesUiState()
}