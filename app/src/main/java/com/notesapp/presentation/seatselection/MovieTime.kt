package com.notesapp.presentation.seatselection

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.notesapp.data.local.entity.MovieTimes
import kotlin.collections.forEach


@Composable
fun MovieTime(genres: List<MovieTimes>, selectedTime: MutableState<String?>) {
    FlowRow(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        genres.forEach { timeItem ->
            val isSelected = timeItem.time == selectedTime.value

            AssistChip(
                onClick = {
                    selectedTime.value = timeItem.time
                },
                label = {
                    Text(
                        timeItem.time,
                        color = if (isSelected) Color.Black else Color.White
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (isSelected) Color.Green else Color.DarkGray
                )
            )
        }
    }

}
