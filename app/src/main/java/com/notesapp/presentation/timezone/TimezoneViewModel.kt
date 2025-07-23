package com.notesapp.presentation.timezone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.local.entity.TimeZoneEntity
import com.notesapp.domain.usecase.GetTimezonesUseCase
import com.notesapp.domain.usecase.RefreshTimezoneUseCase
import com.notesapp.util.ApiResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimezoneViewModel @Inject constructor(private val getTimezoneUseCase: GetTimezonesUseCase,
                                            private val refreshTimezoneUseCase: RefreshTimezoneUseCase
) :
    ViewModel() {

    private var _timezones =
        MutableStateFlow<ApiResource<List<TimeZoneEntity>>>(ApiResource.Loading)
    var timezones: StateFlow<ApiResource<List<TimeZoneEntity>>> = _timezones

    fun getTimezone() {
        viewModelScope.launch {
            getTimezoneUseCase().collect { _timezones.value = it }
        }
    }

    fun refreshTimezone()
    {
        viewModelScope.launch {
            refreshTimezoneUseCase()
        }

    }
}