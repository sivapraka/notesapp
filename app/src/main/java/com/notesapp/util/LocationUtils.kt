package com.notesapp.util

import android.Manifest
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await
import java.util.Locale
import android.location.Address
import android.os.Build
import android.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Suspend extension to get last known location or null on failure.
 */
@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
suspend fun FusedLocationProviderClient.getLastLocationOrNull(): Location? =
    try {
        lastLocation.await()
    } catch (e: Exception) {
        Log.e("TAG", "getLastLocationOrNull: " + e.printStackTrace())
        null
    }


/**
 * Reverse‑geocode lat/lon → "City, COUNTRY"
 * Uses the new listener API on Tiramisu+ and the old API below it.
 */
suspend fun Context.reverseGeocode(lat: Double, lon: Double): String? {
    val geocoder = Geocoder(this, Locale.getDefault())

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        // Asynchronous listener API
        suspendCancellableCoroutine { cont ->
            geocoder.getFromLocation(
                lat, lon, 1,
                object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        val addr = addresses.firstOrNull()
                        cont.resume(addr?.let {
                            "${it.locality ?: it.subAdminArea}"
                            //  Log.e("TAG", "onGeocode: "+it.locality ?: it.subAdminArea )
                        }.toString())
                    }

                    override fun onError(errorMessage: String?) {
                        cont.resume(null)
                    }
                }
            )
            // If the coroutine is cancelled, you could call cont.cancel(), but the Geocoder API
            // does not expose a cancel method, so we just ignore it here.
        }
    } else {
        // Synchronous (blocking) fallback
        geocoder.getFromLocation(lat, lon, 1)
            ?.firstOrNull()
            ?.let { "${it.locality ?: it.subAdminArea}, ${it.countryCode}" }
    }
}