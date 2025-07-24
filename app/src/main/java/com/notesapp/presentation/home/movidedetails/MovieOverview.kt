package com.notesapp.presentation.home.movidedetails

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MovieOverview(overview: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Overview", style = MaterialTheme.typography.titleMedium.copy(color = Color.DarkGray))
        Spacer(modifier = Modifier.height(4.dp))
        Text(overview, style = MaterialTheme.typography.bodyMedium.copy(color = Color.LightGray))
    }
}