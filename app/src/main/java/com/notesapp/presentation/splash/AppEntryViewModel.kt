package com.notesapp.presentation.splash

import androidx.lifecycle.ViewModel
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.notesapp.domain.usecase.CheckAppUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AppEntryViewModel @Inject constructor(
    private val appUpdateUseCase: CheckAppUpdateUseCase
) : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady

    private val _appUpdateInfo = MutableStateFlow<AppUpdateInfo?>(null)
    val appUpdateInfo: StateFlow<AppUpdateInfo?> = _appUpdateInfo

    fun markReady() {
        _isReady.value = true
    }

    fun checkAppUpdate() {
        appUpdateUseCase().addOnSuccessListener { info ->
            if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                _appUpdateInfo.value = info
            }
        }
    }

}