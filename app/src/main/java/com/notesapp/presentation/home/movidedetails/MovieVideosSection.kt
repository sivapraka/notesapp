package com.notesapp.presentation.home.movidedetails

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.notesapp.data.local.entity.MoviesVideos


@Composable
fun MovieVideosSection(videos: List<MoviesVideos?>) {
    val ctxt = LocalContext.current
    Column(Modifier.padding(16.dp)) {
        Text("Videos", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        LazyRow {
            items(videos.filter { it?.site == "YouTube" }) { video ->
                Column(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .width(150.dp)
                        .clickable {
                            val context = ctxt
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.youtube.com/watch?v=${video?.key}")
                            )
                            context.startActivity(intent)
                        }
                ) {
                    AsyncImage(
                        model = "https://img.youtube.com/vi/${video?.key}/0.jpg",
                        contentDescription = video?.name,
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(video?.name.toString(), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}
