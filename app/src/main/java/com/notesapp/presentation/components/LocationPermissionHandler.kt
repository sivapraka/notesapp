package com.notesapp.presentation.components

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat


@Composable
fun LocationPermissionHandler(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted ->
        val allGranted = permissions.all { isGranted[it] == true }
        if (allGranted) {
            Log.e("TAG", "LocationPermissionHandler:  granted" )
            onPermissionGranted()
        } else {
            onPermissionDenied?.invoke()
        }
    }

    LaunchedEffect(Unit) {
        val allPermissionsGranted = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
        if (allPermissionsGranted) {
            onPermissionGranted()
            Log.e("TAG", "LocationPermissionHandler:  granted" )
        } else {
            launcher.launch(permissions.toTypedArray())
        }
    }
}
