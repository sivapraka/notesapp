package com.notesapp.presentation.home.movidedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun MovieBanner(backdropUrl: String, posterUrl: String) {
    Box {
        backdropUrl.let {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500$it",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            }
        posterUrl.let {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w185$it",
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

    }
}


@Composable
fun BookButton(onClicked:() -> Unit) {
    Button(
        onClick = onClicked,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Book Tickets")
    }
}
