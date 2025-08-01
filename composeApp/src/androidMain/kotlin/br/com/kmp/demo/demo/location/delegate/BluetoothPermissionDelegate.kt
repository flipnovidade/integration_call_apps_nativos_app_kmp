package br.com.kmp.demo.demo.location.delegate

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import br.com.kmp.demo.demo.location.model.Permission
import br.com.kmp.demo.demo.location.model.PermissionState
import br.com.kmp.demo.demo.location.uitl.PermissionRequestException
import br.com.kmp.demo.demo.location.util.checkPermissions
import br.com.kmp.demo.demo.location.util.openAppSettingsPage
import br.com.kmp.demo.demo.location.util.providePermissions

internal class BluetoothPermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {

    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, bluetoothPermissions)
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(bluetoothPermissions) {
            throw PermissionRequestException(Permission.BLUETOOTH.name) as Throwable
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.BLUETOOTH)
    }
}

private val bluetoothPermissions: List<String> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
        )
    } else {
        listOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }