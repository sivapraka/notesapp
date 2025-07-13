package com.notesapp.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.notesapp.R

import com.notesapp.domain.model.NotesDataSource
import com.notesapp.presentation.components.*
import com.notesapp.presentation.notes.state.NotesUiState
import com.notesapp.presentation.viewmodel.NoteListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showSheet by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val refreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            AddNoteContent(
                onAdd = { title, content ->
                    viewModel.addNote(title, content)
                    showSheet = false
                },
            )
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            NotesTopAppBar(
                searchQuery = searchQuery,
                onQueryChange = { searchQuery = it }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showSheet = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is NotesUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is NotesUiState.Success -> {
                val notes = state.notes
                if (notes.isEmpty()) {
                    EmptyNotesView(modifier = Modifier.padding(paddingValues))
                } else {
                    val searchResult by remember(notes, searchQuery) {
                        derivedStateOf {
                            if (searchQuery.isBlank()) notes
                            else notes.filter {
                                when (it) {
                                    is NotesDataSource.Header -> true // Keep As it is
                                    is NotesDataSource.Item ->
                                        it.note.title.contains(searchQuery, ignoreCase = true) ||
                                                it.note.description.contains(
                                                    searchQuery,
                                                    ignoreCase = true
                                                )
                                }
                            }
                        }
                    }
                    PullToRefreshBox(
                        state = pullRefreshState,
                        isRefreshing = refreshing,
                        onRefresh = { viewModel.refreshNotes() },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(paddingValues)
                                .background(color = colorResource(R.color.white))
                        ) {
                            items(searchResult) { note ->
                                when (note) {
                                    is NotesDataSource.Header -> {
                                        Text(
                                            text = note.title,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(Color.LightGray)
                                                .padding(8.dp)
                                        )
                                    }

                                    is NotesDataSource.Item -> {
                                        SwipeToDeleteContainer(
                                            onDelete = {
                                                viewModel.deleteNote(note.note.id)
                                                scope.launch {
                                                    val result = snackbarHostState.showSnackbar(
                                                        message = "Note deleted",
                                                        actionLabel = "Undo",
                                                        duration = SnackbarDuration.Short
                                                    )
                                                    if (result == SnackbarResult.ActionPerformed) {
                                                        viewModel.restoreLastDeletedNote()
                                                    }
                                                }
                                            }
                                        ) {
                                            NoteItem(note = note.note)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }

            is NotesUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            is NotesUiState.Adding -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Loading(modifier = Modifier.size(150.dp))
                }
            }
        }


    }
}


