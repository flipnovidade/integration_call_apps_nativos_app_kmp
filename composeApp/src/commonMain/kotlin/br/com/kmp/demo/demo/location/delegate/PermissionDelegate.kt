package br.com.kmp.demo.demo.location.delegate

import br.com.kmp.demo.demo.location.model.PermissionState

internal interface PermissionDelegate {
    fun getPermissionState(): PermissionState
    suspend fun providePermission()
    fun openSettingPage()
}