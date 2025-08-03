package br.com.kmp.demo.demo.ui.viewmodel

import br.com.kmp.demo.demo.location.model.Permission
import br.com.kmp.demo.demo.location.model.PermissionState
import br.com.kmp.demo.demo.location.provider.LocationData
import br.com.kmp.demo.demo.location.provider.LocationProvider
import br.com.kmp.demo.demo.location.service.PermissionsService
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationScreenViewModel(
    val permissionsService: PermissionsService,
    val locationProvider: LocationProvider,
): BaseViewModel(){

    private val _stateLocation = MutableStateFlow<LocationData?>(null)
    val stateLocation: StateFlow<LocationData?> = locationProvider.locationFlow

    init {
        getLocation()
    }

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

    fun getLocation() {
        locationProvider.startLocationUpdates()
        viewModelScope.launch {
            _stateLocation.value = locationProvider.locationFlow.value
            delay(5000)
        }
    }

    fun stop() {
        locationProvider.stopLocationUpdates()
        viewModelScope.cancel()
    }

}


