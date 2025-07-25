package com.notesapp.domain.usecase

import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.notesapp.domain.repository.AppUpdateRepository
import javax.inject.Inject

class CheckAppUpdateUseCase @Inject constructor(
    private val appUpdateRepository: AppUpdateRepository) {
    operator fun invoke(): Task<AppUpdateInfo> {
        return appUpdateRepository.getAppUpdateInfo()

    }
}