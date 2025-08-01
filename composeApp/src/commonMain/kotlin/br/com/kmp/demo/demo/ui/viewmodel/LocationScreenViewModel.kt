package br.com.kmp.demo.demo.ui.viewmodel

import br.com.kmp.demo.demo.location.model.Permission
import br.com.kmp.demo.demo.location.model.PermissionState
import br.com.kmp.demo.demo.location.service.PermissionsService
import kotlinx.coroutines.flow.Flow

class LocationScreenViewModel(val permissionsService: PermissionsService): BaseViewModel(){

    fun checkPermission(permission: Permission): PermissionState{
        return permissionsService.checkPermission(permission)
    }

    fun checkPermissionFlow(permission: Permission): Flow<PermissionState>{
        return permissionsService.checkPermissionFlow(permission)
    }

    suspend fun providePermission(permission: Permission){
        permissionsService.providePermission(permission)
    }

    fun openSettingPage(permission: Permission){
        permissionsService.openSettingPage(permission)
    }

}


