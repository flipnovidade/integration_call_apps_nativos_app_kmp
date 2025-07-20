package br.com.kmp.demo.demo.ui.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import br.com.kmp.demo.demo.permissions.PermissionRequestMyApp
import br.com.kmp.demo.demo.permissions.PermissionResultCallback
import br.com.kmp.demo.demo.ui.components.imageBitmapFromByteArray
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TakePictureViewModel(
    val PERMISSION: String,
    val permissionRequestMyApp: PermissionRequestMyApp): BaseViewModel() {

    private val _granted = MutableStateFlow( false)
    val granted: StateFlow<Boolean> = _granted

    private val _myImageBitmap = MutableStateFlow<ImageBitmap?>(null)
    val myImageBitmap: StateFlow<ImageBitmap?> = _myImageBitmap

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

     fun getBitmapImage(bytes: ByteArray) {
         _myImageBitmap.value = imageBitmapFromByteArray(bytes = bytes, sizeImage = 120.dp)
     }

}
