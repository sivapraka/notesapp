package com.notesapp.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.notesapp.presentation.notes.NoteListScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen()
        }
    }


    @Composable
    fun Screen() {
        MaterialTheme { // Your Compose theme
            Surface(
                modifier = Modifier.Companion.fillMaxSize(),
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


    @Preview
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable

    fun PullToRefreshPreview() {
        var refreshing by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val pullRefreshState = rememberPullToRefreshState()

        PullToRefreshBox(
            state = pullRefreshState,
            isRefreshing = refreshing,
            onRefresh = {
                refreshing = true
                scope.launch {
                    delay(2000)
                    refreshing = false
                }
            },
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(20) {
                    Text("Item $it", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }


}