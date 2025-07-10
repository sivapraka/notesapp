package com.notesapp.ui.theatre


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.*

@Composable
fun TheatreScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text("Theatre Screen", modifier = Modifier.padding(16.dp))
    }
}