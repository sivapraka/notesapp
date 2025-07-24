package com.notesapp.presentation.home.movidedetails

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.notesapp.data.local.entity.ImdbCollections

@Composable
fun BelongsToCollectionSection(collection: ImdbCollections?) {
    Column(Modifier.padding(16.dp)) {
        Text("Part of Collection:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        collection?.let { Text(it.name, color = Color.LightGray) }
    }
}