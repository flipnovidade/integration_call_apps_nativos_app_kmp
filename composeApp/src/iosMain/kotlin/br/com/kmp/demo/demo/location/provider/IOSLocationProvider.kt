package br.com.kmp.demo.demo.location.provider

import br.com.kmp.demo.demo.ui.components.KmpLogger
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.*
import kotlinx.cinterop.ExportObjCClass
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.useContents
import platform.darwin.NSObject
import kotlinx.cinterop.useContents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import objcnames.classes.Protocol
import platform.CoreLocation.CLLocation
import platform.darwin.NSUInteger

class IOSLocationProvider : LocationProvider {

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