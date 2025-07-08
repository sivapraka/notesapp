package com.notesapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun AddNoteContent(
    onAdd: (String, String) -> Unit

) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var showTitleError by remember { mutableStateOf(false) }
    var showContentError by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text("Add Note", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        val maxTitleLength = 20
        val maxDescLength = 200
        OutlinedTextField(
            value = title,
            onValueChange = {
                if (it.length <= maxTitleLength) title = it
                if (showTitleError && it.isNotBlank()) showTitleError = false
            },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            isError = showTitleError,
            singleLine = true,
            supportingText = {
                if (showTitleError) {
                    Text("Title cannot be empty", color = MaterialTheme.colorScheme.error)
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = content,
            onValueChange = {
                if (it.length <= maxDescLength) content = it
                if (showContentError && it.isNotBlank()) showContentError = false
            },
            label = { Text("Content") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            isError = showContentError,
            supportingText = {
                if (showContentError) {
                    Text("Content cannot be empty", color = MaterialTheme.colorScheme.error)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                showTitleError = title.isBlank()
                showContentError = content.isBlank()
                if (!showTitleError && !showContentError) {
                    onAdd(title, content)
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }
    }
}
