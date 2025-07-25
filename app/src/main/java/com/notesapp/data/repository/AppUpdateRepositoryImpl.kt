package com.notesapp.data.repository

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.*
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.notesapp.domain.repository.AppUpdateRepository
import javax.inject.Inject

class AppUpdateRepositoryImpl @Inject constructor(
    private val appUpdateManager: AppUpdateManager,
) : AppUpdateRepository {
    override fun getAppUpdateInfo(): Task<AppUpdateInfo> {
        return appUpdateManager.appUpdateInfo
    }

    fun startImmediateUpdate(info: AppUpdateInfo, activity: Activity) {
        val updateOptions = AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
        appUpdateManager.startUpdateFlow(info, activity, updateOptions)
    }

    override fun startFlexibleUpdate(activity: Activity, info: AppUpdateInfo) {
        val options = AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build()
        appUpdateManager.startUpdateFlow(info, activity, options)
    }

    override fun registerListener(listener: InstallStateUpdatedListener) {
        appUpdateManager.registerListener(listener)
    }

    override fun unregisterListener(listener: InstallStateUpdatedListener) {
        appUpdateManager.unregisterListener(listener)
    }

    override fun completeUpdate() {
        appUpdateManager.completeUpdate()
    }


}