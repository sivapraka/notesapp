package com.notesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.notesapp.data.local.dao.MoviesDao
import com.notesapp.data.local.dao.NoteDao
import com.notesapp.data.local.entity.Movies
import com.notesapp.data.local.entity.Note
import com.notesapp.data.local.entity.Rating


@Database(entities = [Note::class, Movies::class, Rating::class], version = 2)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun movieDao(): MoviesDao
}