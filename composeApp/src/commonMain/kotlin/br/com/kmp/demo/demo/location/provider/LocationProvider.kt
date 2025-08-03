package br.com.kmp.demo.demo.location.provider

import kotlinx.coroutines.flow.StateFlow

interface LocationProvider {
    fun startLocationUpdates()
    fun stopLocationUpdates()
    fun getLastKnownLocation(): LocationData?

    val locationFlow: StateFlow<LocationData?>
}

data class LocationData(
    val latitude: Double,
    val longitude: Double
)