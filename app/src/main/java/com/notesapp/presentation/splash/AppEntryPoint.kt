package com.notesapp.presentation.splash

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import com.notesapp.presentation.home.MainScreen


@SuppressLint("NewApi")
@Composable
fun AppEntryPoint() {
    var showMainScreen by remember { mutableStateOf(false) }
    if (showMainScreen) {
        MainScreen()
    } else {
        SplashScreen(
            onPermissionGranted = {
                showMainScreen = true
            }
        )
    }
}