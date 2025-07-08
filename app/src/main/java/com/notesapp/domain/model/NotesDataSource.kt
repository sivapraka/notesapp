package com.notesapp.domain.model


sealed class NotesDataSource {
    data class Header(val title: String) : NotesDataSource()
    data class Item(val note: Notes) : NotesDataSource()
}