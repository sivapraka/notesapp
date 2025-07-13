package com.notesapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Face
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest

@Composable
fun ModernPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
    )
}

@Composable
fun ModernErrorImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.Red.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.TwoTone.Face,
            contentDescription = "Image load failed",
            tint = Color.Red.copy(alpha = 0.6f),
            modifier = Modifier.size(32.dp)
        )
    }
}


@Composable
fun ModernCoilImage(
    imageUrl: String,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(240.dp)
        .clip(RoundedCornerShape(16.dp)),
    contentDescription: String? = null
) {
    val context = LocalContext.current
    var imageState by remember { mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty) }
    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Fit,
            onState = {
                imageState = it
            }
        )

        when (imageState) {
            is AsyncImagePainter.State.Loading -> {
                ModernPlaceholder(modifier = Modifier.matchParentSize())
            }

            is AsyncImagePainter.State.Error -> {
                ModernErrorImage(modifier = Modifier.matchParentSize())
            }

            else -> Unit
        }
    }
}




