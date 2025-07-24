package com.notesapp.presentation.home.movidedetails

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.notesapp.data.local.entity.ImdbGenres
import kotlin.collections.forEach

@Composable
fun MovieGenres(genres: List<ImdbGenres>) {
    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        genres.forEach {
            AssistChip(
                onClick = {},
                label = { Text(it.name, color = Color.White) },
                colors = AssistChipDefaults.assistChipColors(containerColor = Color.DarkGray)
            )
        }
    }
}
