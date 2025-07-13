package com.notesapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.mapper.toDomain
import com.notesapp.data.mapper.toEntity
import com.notesapp.domain.model.Notes
import com.notesapp.domain.model.NotesDataSource
import com.notesapp.domain.usecase.NoteUseCase
import com.notesapp.presentation.notes.state.NotesUiState
import com.notesapp.util.toReadableDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(private val useCase: NoteUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<NotesUiState>(NotesUiState.Loading)
    val uiState: StateFlow<NotesUiState> = _uiState
    private var lastDeletedNote: Notes? = null
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        viewModelScope.launch {
            loadNotes()
        }
    }

    fun refreshNotes() {
        viewModelScope.launch {
            _isRefreshing.value = true
            delay(1500) // Simulate API call
            loadNotes()
            _isRefreshing.value = false
        }
    }

    suspend fun loadNotes() {
        try {
            val notes = useCase.getNotesUseCase().first() // <-- Only get latest snapshot
            val grouped = notes.map { it.toDomain() }
                .groupBy { it.date.toReadableDate() }
            val mergedList = mutableListOf<NotesDataSource>()
            grouped.forEach { (date, notesForDate) ->
                mergedList.add(NotesDataSource.Header(date))
                mergedList.addAll(notesForDate.map { NotesDataSource.Item(it) })
            }
            _uiState.value = NotesUiState.Success(mergedList)

        } catch (e: Exception) {
            _uiState.value = NotesUiState.Error("Failed to load notes.")
        }
    }

    fun addNote(title: String, content: String) {
        _uiState.value = NotesUiState.Adding
        try {
            viewModelScope.launch {
                delay(2000L)
                val note = Notes(
                    title = title,
                    description = content
                )
                // Save locally
                useCase.addNotesUseCase(note.toEntity())
                loadNotes()
            }
        } catch (e: Exception) {
            _uiState.value = NotesUiState.Error("Failed to add note.")
        }
    }

    fun deleteNote(note: Int) {
        viewModelScope.launch {
            lastDeletedNote = (_uiState.value as? NotesUiState.Success)?.notes.orEmpty()
                .filterIsInstance<NotesDataSource.Item>().map { it.note }
                .firstOrNull { it.id == note }
            useCase.deleteNotesUseCase(note)

        }
    }

    fun restoreLastDeletedNote() {
        viewModelScope.launch {
            lastDeletedNote?.let {
                useCase.addNotesUseCase(it.toEntity())
                loadNotes()
                lastDeletedNote = null
            }
        }
    }
}