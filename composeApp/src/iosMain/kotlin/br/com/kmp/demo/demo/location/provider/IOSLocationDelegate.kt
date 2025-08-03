package br.com.kmp.demo.demo.location.provider

import br.com.kmp.demo.demo.ui.components.KmpLogger
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ExportObjCClass
import kotlinx.cinterop.useContents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.darwin.NSObject

@OptIn(BetaInteropApi::class)
@ExportObjCClass
class IOSLocationDelegate : NSObject(), CLLocationManagerDelegateProtocol {

    private val _locationFlow = MutableStateFlow<LocationData?>(null)
    val locationFlow: StateFlow<LocationData?> = _locationFlow

    private val locationManager = CLLocationManager()

    private var lastLocation: CLLocation? = null

    init {
        locationManager.delegate = this
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
    }

    fun startLocationUpdates() {
        if (CLLocationManager.locationServicesEnabled()) {
            when (locationManager.authorizationStatus) {
                kCLAuthorizationStatusNotDetermined -> locationManager.requestWhenInUseAuthorization()
                kCLAuthorizationStatusAuthorizedWhenInUse,
                kCLAuthorizationStatusAuthorizedAlways -> locationManager.startUpdatingLocation()
                else -> println("Location not authorized")
            }
        }
    }

    fun stopLocationUpdates() {
        locationManager.stopUpdatingLocation()
    }

    @OptIn(ExperimentalForeignApi::class)
    fun getLastKnownLocation(): LocationData? {
        val loc = lastLocation ?: return null
        return LocationData(
            latitude = loc.coordinate.useContents { latitude },
            longitude = loc.coordinate.useContents { longitude },
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        val locations = didUpdateLocations
        lastLocation = locations.lastOrNull() as CLLocation?

        val latitude = lastLocation!!.coordinate.useContents { latitude }
        val longitude = lastLocation!!.coordinate.useContents { longitude }

        _locationFlow.value = LocationData(
            latitude = latitude,
            longitude = longitude
        )

        KmpLogger.d("locationManager","Updated location: $latitude, $longitude")

    }

    override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
        val status = manager.authorizationStatus
        KmpLogger.d("locationManagerDidChangeAuthorization","Authorization changed: $status")
        if (status == kCLAuthorizationStatusAuthorizedWhenInUse || status == kCLAuthorizationStatusAuthorizedAlways) {
            manager.startUpdatingLocation()
        }
    }

}