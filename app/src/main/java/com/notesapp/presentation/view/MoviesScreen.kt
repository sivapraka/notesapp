package com.notesapp.presentation.view


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.google.android.play.core.appupdate.*
import com.google.android.play.core.install.model.*
import com.notesapp.presentation.splash.AppEntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MoviesScreen : ComponentActivity() {
    private lateinit var appUpdateManager: AppUpdateManager
    private var snackbarHostState: SnackbarHostState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appUpdateManager = AppUpdateManagerFactory.create(this)
        setContent {
            val hostState = remember { SnackbarHostState() }
            snackbarHostState = hostState

            AppEntryPoint(
                updateHandler = { info ->
                    appUpdateManager.startUpdateFlow(
                        info,
                        this,
                        AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build()
                    )
                },
                snackbarHostState = hostState // pass it down to MainScreen if needed
            )

            // Optional: Global Snackbar
            Box(Modifier.fillMaxSize()) {
                SnackbarHost(hostState)
            }
        }

        observeUpdateState()
    }

    private fun observeUpdateState() {
        appUpdateManager.registerListener { state ->
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                lifecycleScope.launch {
                    snackbarHostState?.showSnackbar(
                        message = "Update downloaded. Restarting...",
                        duration = SnackbarDuration.Short
                    )
                    delay(1500)
                    appUpdateManager.completeUpdate()
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        appUpdateManager.unregisterListener { }
    }

}