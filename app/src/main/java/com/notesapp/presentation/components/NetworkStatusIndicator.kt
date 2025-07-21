package com.notesapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*

@Composable
fun NetworkStatusIndicator(modifier: Modifier = Modifier, isConnected: Boolean) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = if (isConnected) Color(0xFF4CAF50) else Color(0xFFF44336),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = if (isConnected) "Online" else "Offline",
            color = Color.White,
            fontSize = 12.sp
        )
    }
}