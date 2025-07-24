package com.notesapp.presentation.home.movidedetails

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.notesapp.data.local.entity.ImdbMoviesDetails

@Composable
fun MovieTitleSection(movie: ImdbMoviesDetails) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = movie.title!!,
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.Black)
        )
        Text(
            text = "${movie.release_date} • ${movie.runtime} min",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )
    }
}