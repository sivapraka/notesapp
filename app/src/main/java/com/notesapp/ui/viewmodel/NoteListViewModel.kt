package com.notesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.mapper.toDomain
import com.notesapp.data.mapper.toEntity
import com.notesapp.domain.model.Notes
import com.notesapp.domain.usecase.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(private val useCase: NoteUseCase) : ViewModel() {
    private val _notes = MutableStateFlow<List<Notes>>(emptyList())
    val notes: StateFlow<List<Notes>> = _notes

    init {
        viewModelScope.launch {
            loadNotes()
        }
    }

    suspend fun loadNotes() {
        useCase.getNotesUseCase().collect { notes ->
            _notes.value = notes.map { it.toDomain() }
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            val note = Notes(
                title = title,
                description = content
            )
            // Save locally
            useCase.addNotesUseCase(note.toEntity())
            loadNotes()
        }
    }

    fun deleteNote(note: Int) {
        viewModelScope.launch {
            useCase.deleteNotesUseCase(note)
        }
    }
}