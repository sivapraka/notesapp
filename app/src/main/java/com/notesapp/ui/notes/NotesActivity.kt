package com.notesapp.ui.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import com.notesapp.ui.notes.noteslist.NoteListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen()
        }
    }


    @Composable
    fun Screen() {
        MaterialTheme{ // Your Compose theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.surface
            ) {
                NoteListScreen()
            }
        }
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        Screen()
    }
}