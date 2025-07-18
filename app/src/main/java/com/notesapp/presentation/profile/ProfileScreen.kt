package com.notesapp.presentation.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import com.notesapp.presentation.viewmodel.ImdbMoviesViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun ProfileScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        TamilMovieListScreen()
    }
}

@Composable
fun TamilMovieListScreen(viewModel: ImdbMoviesViewModel = hiltViewModel()) {
    val movies = viewModel.movies.collectAsLazyPagingItems()

    LazyColumn {
        items(movies.itemCount) { index ->
            val movie = movies[index]
            movie?.let {
                Text(text = it.title ?: "No Title")
            }
        }

        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                loadState.append is LoadState.Error -> {
                    val e = movies.loadState.append as LoadState.Error
                    item { Text("Error: ${e.error.message}") }
                }
            }
        }
    }
}
