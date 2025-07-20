package br.com.kmp.demo.demo.ui.viewmodel

import br.com.kmp.demo.demo.permissions.PermissionRequestMyApp
import br.com.kmp.demo.demo.permissions.PermissionResultCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TakePictureViewModel(
    val PERMISSION: String,
    val permissionRequestMyApp: PermissionRequestMyApp): BaseViewModel() {

    private val _granted = MutableStateFlow( false)
    val granted: StateFlow<Boolean> = _granted

    private val _listImage = MutableStateFlow<List<String>>( emptyList())
    val listImage: StateFlow<List<String>> = _listImage

    init {
        isPermissionGranted()
    }

    fun requestPermission() {
        permissionRequestMyApp.requestPermission(PERMISSION,object : PermissionResultCallback {
            override fun onPermissionGranted() {
                _granted.value = true
            }

            override fun onPermissionDenied() {
                _granted.value = false
            }
        })
    }

    fun isPermissionGranted(): Boolean {
        val resultCheck = permissionRequestMyApp.isPermissionGranted(PERMISSION)
        _granted.value = resultCheck
        return  _granted.value
    }


}
