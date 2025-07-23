package com.notesapp.presentation.location

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.notesapp.presentation.components.LocationPermissionHandler


@Composable
fun LocationScreen(viewModel: LocationViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val location by viewModel.location.collectAsState()
    val placeName by viewModel.placeName.collectAsState()

    LocationPermissionHandler(
        onPermissionGranted = {
            viewModel.fetchLocationAndPlace()
        },
        onPermissionDenied = {
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        if (location != null) {
            Text("Latitude: ${location?.latitude}")
            Text("Longitude: ${location?.longitude}")
            if (placeName.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Place: $placeName")
            }
        } else {
            Text("Fetching location...")
        }
    }
}

