package br.com.kmp.demo.demo.location.provider

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AndroidLocationProvider(
    private val context: Context
) : LocationProvider {

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val _locationFlow = MutableStateFlow<LocationData?>(null)
    override val locationFlow: StateFlow<LocationData?> = _locationFlow

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let { loc ->
                _locationFlow.value = LocationData(
                    latitude = loc.latitude,
                    longitude = loc.longitude
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun startLocationUpdates() {

        val request = LocationRequest
            .Builder(Priority.PRIORITY_HIGH_ACCURACY, 10_000)
            .setIntervalMillis(5_000)
            .build()

        fusedLocationClient.requestLocationUpdates(
            request,
            locationCallback,
            null
        )

    }

    override fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun getLastKnownLocation(): LocationData? {
        val loc = locationFlow.value ?: return null
        return LocationData(
            latitude = loc.latitude,
            longitude = loc.longitude
        )
    }
}