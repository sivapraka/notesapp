package com.notesapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.notesapp.ui.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesScreen: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieScreen()
        }
    }
    @Composable
    fun MovieScreen(viewModel: MovieViewModel = hiltViewModel()) {
        val movieState = viewModel.movieState.collectAsState()

        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = { viewModel.fetchMovie("Inception") }) {
                Text("Fetch Movie")
            }

            movieState.value?.fold(
                onSuccess = { movie ->
                    Column {
                        Text("Title: ${movie.title}")
                        Text("Plot: ${movie.plot}")
                        //AsyncImage(model = movie.poster, contentDescription = null)
                    }
                },
                onFailure = { throwable ->
                    Text("Error: ${throwable.message}")
                }
            )
        }
    }



}