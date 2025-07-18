package com.notesapp.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.notesapp.BuildConfig
import com.notesapp.data.local.entity.ImdbMovies
import com.notesapp.presentation.viewmodel.ImdbMoviesViewModel

@Composable
fun ProfileScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        MovieListScreen()
    }
}

@Composable
fun MovieListScreen(viewModel: ImdbMoviesViewModel = hiltViewModel()) {
    val movies = viewModel.movies.collectAsLazyPagingItems()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies.itemCount) { index ->
            val movie = movies[index]
            movie?.let {
                MovieCardDetails(movie)
            }
        }

        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item(span = { GridItemSpan(2) }) {
                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item(span = { GridItemSpan(2) }) {
                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }

                loadState.append is LoadState.Error -> {
                    val e = movies.loadState.append as LoadState.Error
                    item(span = { GridItemSpan(2) }) {
                        Text("Error: ${e.error.message}")
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCardDetails(movie: ImdbMovies) {
    Column(
        modifier = Modifier
            .width(180.dp)
    ) {
        Box {
            val imageurl = "${BuildConfig.Image_URL}${movie.poster_path!!}"
            AsyncImage(
                model = imageurl,
                contentDescription = movie.title,
                modifier = Modifier
                    .height(260.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            if (movie.video) {
                Text(
                    text = "Video",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .background(Color.Red, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }


        Row(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "${movie.vote_average}  ${movie.vote_count}",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            movie.title.toString(),
            fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis
        )

        movie.release_date?.let {
            Text(text = "Released $it", fontSize = 12.sp, color = Color.Gray)
        }

    /*    Text(
            text = movie.original_language.toString(),
            fontSize = 12.sp,
            color = Color.Gray
        )*/
    }
}
