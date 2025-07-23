package com.notesapp

import android.app.Application
import com.notesapp.presentation.startup.StartupEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import timber.log.Timber

@HiltAndroidApp
class NotesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        val initializer = EntryPointAccessors.fromApplication(
            this,
            StartupEntryPoint::class.java
        ).startupInitializer()

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            initializer.init()
        }
    }
}