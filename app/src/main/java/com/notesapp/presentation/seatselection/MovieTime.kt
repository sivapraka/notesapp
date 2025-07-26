package com.notesapp.presentation.seatselection

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.notesapp.data.local.entity.MovieTimes
import kotlin.collections.forEach


@Composable
fun MovieTime(genres: List<MovieTimes>) {
    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        genres.forEach {
            AssistChip(
                onClick = {},
                label = { Text(it.time, color = Color.White) },
                colors = AssistChipDefaults.assistChipColors(containerColor = Color.DarkGray)
            )
        }
    }
}
