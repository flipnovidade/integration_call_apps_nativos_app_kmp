package br.com.kmp.demo.demo.location.delegate

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.provider.Settings
import br.com.kmp.demo.demo.location.model.Permission
import br.com.kmp.demo.demo.location.model.PermissionState
import br.com.kmp.demo.demo.location.uitl.CannotOpenSettingsException
import br.com.kmp.demo.demo.location.util.openPage

internal class BluetoothServicePermissionDelegate(
    private val context: Context,
    private val bluetoothAdapter: BluetoothAdapter?,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return if (bluetoothAdapter?.isEnabled == true)
            PermissionState.GRANTED else PermissionState.DENIED
    }

    override suspend fun providePermission() {
        openSettingPage()
    }

    override fun openSettingPage() {
        context.openPage(
            action = Settings.ACTION_BLUETOOTH_SETTINGS,
            onError = { throw CannotOpenSettingsException(Permission.BLUETOOTH_SERVICE_ON.name) as Throwable }
        )
    }
}