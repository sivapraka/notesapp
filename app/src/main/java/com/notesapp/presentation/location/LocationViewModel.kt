package com.notesapp.presentation.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.notesapp.util.getLastLocationOrNull
import com.notesapp.util.reverseGeocode
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val fusedClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

    private val _placeName = MutableStateFlow<String>("")
    val placeName: StateFlow<String> = _placeName

    /**
     * Call this to trigger a one‑time fetch & reverse‑geocode
     */
    @SuppressLint("MissingPermission")
    fun fetchLocationAndPlace() {
        viewModelScope.launch {
            // 1. Get last known location
            var loc = withContext(Dispatchers.IO) {
                fusedClient.getLastLocationOrNull()
            }
            // If null, request current location
            if (loc == null) {
                val cancellationToken = com.google.android.gms.tasks.CancellationTokenSource()
                loc = withContext(Dispatchers.IO) {
                    fusedClient.getCurrentLocation(
                        com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                        cancellationToken.token
                    ).await()
                }
            }

            _location.value = loc
            Log.e("TAG", "fetchLocationAndPlace: 1 {$loc.latitude} " )
            Log.e("TAG", "fetchLocationAndPlace: 1 {$loc.longitude} " )
            // 2. Reverse‑geocode to “City, COUNTRY” if location not null
            loc?.let {
                val name = withContext(Dispatchers.IO) {
                    context.reverseGeocode(it.latitude, it.longitude)
                } ?: "Unknown location"
                _placeName.value = name
                Log.e("TAG", "fetchLocationAndPlace: "+name )
            }
        }
    }
}
