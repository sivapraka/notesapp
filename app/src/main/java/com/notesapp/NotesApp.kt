package com.notesapp

import android.app.Application
import com.notesapp.presentation.startup.StartupConfigInitializer
import com.notesapp.presentation.startup.StartupEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class NotesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val initializer = EntryPointAccessors.fromApplication(
            this,
            StartupEntryPoint::class.java
        ).startupInitializer()

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            initializer.init()
        }
    }
}