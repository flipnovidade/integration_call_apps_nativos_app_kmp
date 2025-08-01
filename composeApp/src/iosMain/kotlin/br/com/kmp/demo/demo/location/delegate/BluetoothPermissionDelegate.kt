package br.com.kmp.demo.demo.location.delegate

import br.com.kmp.demo.demo.location.model.PermissionState
import br.com.kmp.demo.demo.location.util.openAppSettingsPage
import platform.CoreBluetooth.CBCentralManager
import platform.CoreBluetooth.CBManagerAuthorizationAllowedAlways
import platform.CoreBluetooth.CBManagerAuthorizationDenied
import platform.CoreBluetooth.CBManagerAuthorizationNotDetermined
import platform.CoreBluetooth.CBManagerAuthorizationRestricted

internal class BluetoothPermissionDelegate : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return when (CBCentralManager.authorization) {
            CBManagerAuthorizationNotDetermined -> PermissionState.NOT_DETERMINED
            CBManagerAuthorizationAllowedAlways, CBManagerAuthorizationRestricted -> PermissionState.GRANTED
            CBManagerAuthorizationDenied -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        CBCentralManager().authorization()
    }

    override fun openSettingPage() {
        openAppSettingsPage()
    }
}