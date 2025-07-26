package com.notesapp.presentation.home.movidedetails

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.notesapp.presentation.seatselection.SeatSelectionScreen
import com.notesapp.util.ApiResource

@Composable
fun MovieDetailScreen(
    movieId: Int,
    navController: NavController,
    viewModel: ImdbMoviesDetailsViewModel = hiltViewModel()
) {
    val st by viewModel.movieDetails.collectAsState()
    val videoState by viewModel.videos.collectAsState()
    LaunchedEffect(movieId) {
        viewModel.movieDetails(movieId)
        viewModel.loadVideos(movieId)
    }
    when (val state = st) {
        is ApiResource.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        is ApiResource.Error -> {
            Text("Something went wrong!", Modifier.padding(16.dp))
        }

        is ApiResource.Success -> {
            val movie = state.data
            movie?.let {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        MovieBanner(
                            backdropUrl = movie.backdrop_path!!,
                            posterUrl = movie.poster_path!!
                        )
                        MovieTitleSection(movie)
                        MovieGenres(movie.genres)
                        videoState.let {
                            when (val video = it) {
                                is ApiResource.Loading -> null
                                is ApiResource.Error -> null
                                is ApiResource.Success -> {
                                    video.data?.results?.let { it -> MovieVideosSection(it) }
                                }
                            }
                        }
                        MovieOverview(movie.overview!!)
                        MovieDetails(movie)
                        ProductionCompanies(movie.production_companies)
                        BelongsToCollectionSection(movie.belongs_to_collection)
                        BookButton {
                            val theatreId = "theatre_001" // You can later let user pick this
                            val showId = "2025-07-26_19:30" // Or use actual selected time/date
                            Log.e("TAG", "MovieDetailScreen: " + "BookButton")
                            navController.navigate("seatSelection/$movieId/$theatreId/$showId")
                        }
                    }
                }
            } ?: run {
                Text("Something went wrong!", Modifier.padding(16.dp))
            }
        }
    }
}
