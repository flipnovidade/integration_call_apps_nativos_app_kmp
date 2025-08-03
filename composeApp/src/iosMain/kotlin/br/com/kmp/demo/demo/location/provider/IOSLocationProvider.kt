package br.com.kmp.demo.demo.location.provider

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.StateFlow

class IOSLocationProvider() : LocationProvider {

    private val delegate = IOSLocationDelegate()
    override val locationFlow: StateFlow<LocationData?> = delegate.locationFlow

    override fun startLocationUpdates() {
        delegate.startLocationUpdates()
    }

    override fun stopLocationUpdates() {
        delegate.stopLocationUpdates()
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun getLastKnownLocation(): LocationData? {
        return delegate.getLastKnownLocation()
    }

}