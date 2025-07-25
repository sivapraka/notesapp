package com.notesapp.domain.repository

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.install.InstallStateUpdatedListener

interface AppUpdateRepository {
    fun getAppUpdateInfo(): Task<AppUpdateInfo>

    fun registerListener(listener: InstallStateUpdatedListener)
    fun unregisterListener(listener: InstallStateUpdatedListener)
    fun completeUpdate()
    fun startFlexibleUpdate(activity: Activity, info: AppUpdateInfo)
}