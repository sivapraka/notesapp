package com.notesapp.presentation.bookings


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BookingScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text("Booking Screen", modifier = Modifier.padding(16.dp))
    }
}