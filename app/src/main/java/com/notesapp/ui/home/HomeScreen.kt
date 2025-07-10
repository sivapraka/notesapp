package com.notesapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.notesapp.ui.components.ModernCoilImage
import com.notesapp.ui.viewmodel.MovieViewModel


@Composable
fun HomeScreen(viewModel: MovieViewModel = hiltViewModel()) {
    val movieState = viewModel.movieState.collectAsState()
    val movies = listOf(
        "https://cdn4.vectorstock.com/i/1000x1000/39/23/cinema-and-movie-banner-vector-21563923.jpg",
        "https://static.vecteezy.com/system/resources/previews/002/236/321/non_2x/movie-trendy-banner-vector.jpg",
        "https://img.freepik.com/free-vector/movie-making-process-concept-cartoon-flat-illustration_87771-7362.jpg?size=626&ext=jpg"
    )

    Column(modifier = Modifier.Companion.padding(16.dp)) {

        Banner(movies)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.fetchMovie("Inception") }) {
            Text("Fetch Movie")
        }
        movieState.value?.fold(
            onSuccess = { movie ->
                Box() {
                    Column {
                        Text("Title: ${movie.Title}")
                        Spacer(modifier = Modifier.height(14.dp))
                        ModernCoilImage(movie.Poster, contentDescription = movie.Plot)
                        Spacer(modifier = Modifier.height(14.dp))
                        Text("Plot: ${movie.Plot}")
                    }
                }
            },
            onFailure = { throwable ->
                Text("Error: ${throwable.message}")
            }
        )
    }
}