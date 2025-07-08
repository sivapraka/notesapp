package com.notesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.mapper.toDomain
import com.notesapp.data.mapper.toEntity
import com.notesapp.domain.model.Notes
import com.notesapp.domain.model.NotesDataSource
import com.notesapp.domain.usecase.NoteUseCase
import com.notesapp.ui.notes.state.NotesUiState
import com.notesapp.utils.toReadableDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(private val useCase: NoteUseCase) : ViewModel() {
    private val _notes = MutableStateFlow<List<NotesDataSource>>(emptyList())
    val notes: StateFlow<List<NotesDataSource>> = _notes
    private val _uiState = MutableStateFlow<NotesUiState>(NotesUiState.Loading)
    val uiState: StateFlow<NotesUiState> = _uiState

    init {
        viewModelScope.launch {
            loadNotes()
        }
    }

    suspend fun loadNotes() {
        try {
            useCase.getNotesUseCase().collect { notes ->
                val grouped = notes.map { it.toDomain() }
                    .groupBy { it.date.toReadableDate() }
                val mergedList = mutableListOf<NotesDataSource>()
                grouped.forEach { (date, notes) ->
                    mergedList.add(NotesDataSource.Header(date))
                    mergedList.addAll(notes.map { NotesDataSource.Item(it) })
                }
                _notes.value = mergedList
                _uiState.value = NotesUiState.Success(mergedList)
            }
        } catch (e: Exception) {
            _uiState.value = NotesUiState.Error("Failed to load notes.")
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