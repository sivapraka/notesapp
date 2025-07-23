package com.notesapp.presentation.splash

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.notesapp.presentation.components.LocationPermissionHandler
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    var hasPermission by remember { mutableStateOf(false) }

    // Handle permission
    LocationPermissionHandler(
        onPermissionGranted = {
            hasPermission = true
        },
        onPermissionDenied = {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    )

    // Trigger navigation once permission is granted
    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            delay(1000) // Optional: to show splash for a second
            onPermissionGranted()
        }
    }

    // Splash UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
