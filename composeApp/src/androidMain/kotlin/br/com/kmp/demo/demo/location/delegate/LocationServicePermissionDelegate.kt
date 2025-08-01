package br.com.kmp.demo.demo.location.delegate

import android.content.Context
import android.location.LocationManager
import android.provider.Settings
import br.com.kmp.demo.demo.location.model.Permission
import br.com.kmp.demo.demo.location.model.PermissionState
import br.com.kmp.demo.demo.location.uitl.CannotOpenSettingsException
import br.com.kmp.demo.demo.location.util.openPage

internal class LocationServicePermissionDelegate(
    private val context: Context,
    private val locationManager: LocationManager,
) : PermissionDelegate {

    override fun getPermissionState(): PermissionState {
        val granted = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return if (granted)
            PermissionState.GRANTED else PermissionState.DENIED
    }

    override suspend fun providePermission() {
        openSettingPage()
    }

    override fun openSettingPage() {
        context.openPage(
            action = Settings.ACTION_LOCATION_SOURCE_SETTINGS,
            onError = { throw CannotOpenSettingsException(Permission.LOCATION_SERVICE_ON.name) as Throwable }
        )
    }
}