package com.notesapp.presentation.components


import android.Manifest
import android.location.Location
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.notesapp.util.getLastLocationOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun LocationFetcher(onLocation: (Location) -> Unit) {
    val context = LocalContext.current
    val fusedClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    // 1. Track whether permission is granted
    var permissionGranted by remember { mutableStateOf(false) }

    // 2. Launcher for permission request
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        // This callback is NOT @Composable—just update our state
        permissionGranted = granted
    }

    // 3. On first composition, check or request permission
    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                permissionGranted = true
            }
            else -> {
                launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
    // 4. When permissionGranted flips to true, fetch location
    LaunchedEffect(permissionGranted) {
        if (permissionGranted) {
            // Run the Task.await() on IO
            val loc = withContext(Dispatchers.IO) {
                fusedClient.getLastLocationOrNull()
            }
            loc?.let(onLocation)
           // Log.e("TAG", "LocationFetcher: "+loc.let { " ${loc.latitude}  ${loc.latitude } "} )
        }
    }
}