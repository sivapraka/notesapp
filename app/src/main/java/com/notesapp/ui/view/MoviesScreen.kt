package com.notesapp.ui.view


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.notesapp.ui.components.ModernCoilImage
import com.notesapp.ui.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviesScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieScreen()
        }
    }

    @Composable
    fun MovieScreen(viewModel: MovieViewModel = hiltViewModel()) {
        val movieState = viewModel.movieState.collectAsState()

        Column(modifier = Modifier.Companion.padding(16.dp)) {
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


}