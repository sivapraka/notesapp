package com.notesapp.presentation.home.movidedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.notesapp.data.local.entity.ImdbMoviesDetails

@Composable
fun MovieDetails(movie: ImdbMoviesDetails) {
    Column(Modifier.padding(16.dp)) {
        Text("Runtime: ${movie.runtime} mins")
        Text("Languages:")
        FlowRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            movie.spoken_languages.forEach {
                AssistChip(
                    onClick = {},
                    label = { Text(it.english_name!!, color = Color.White) },
                    colors = AssistChipDefaults.assistChipColors(containerColor = Color.DarkGray)
                )
            }
        }
        Text("Release Date: ${movie.release_date}")
    }
}
