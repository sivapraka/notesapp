package com.notesapp.presentation.splash

import android.annotation.SuppressLint
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.notesapp.presentation.home.MainScreen


@SuppressLint("NewApi")
@Composable
fun AppEntryPoint(
    viewModel: AppEntryViewModel = hiltViewModel(),
    updateHandler: (AppUpdateInfo) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val isReady by viewModel.isReady.collectAsState()
    val appUpdateInfo by viewModel.appUpdateInfo.collectAsState()

    LaunchedEffect(true) {
        viewModel.checkAppUpdate()
    }
    when {
        appUpdateInfo != null -> {
            // Call update handler (Activity will handle starting flow)
            LaunchedEffect(Unit) {
                updateHandler(appUpdateInfo!!)
            }
        }

        isReady -> {
            MainScreen(snackbarHostState = snackbarHostState)
        }

        else -> {
            SplashScreen(
                onPermissionGranted = {
                    viewModel.markReady()
                }
            )
        }
    }
}