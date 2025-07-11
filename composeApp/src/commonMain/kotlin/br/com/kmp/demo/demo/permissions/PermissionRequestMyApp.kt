@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package br.com.kmp.demo.demo.permissions

class PermissionRequestMyApp {

    private var listener: PermissionsListener? = null

    fun setListener(listener: PermissionsListener) {
        this.listener = listener
    }

    fun requestPermission(permission: String, callback: PermissionResultCallback) {
        listener?.requestPermission(permission = permission, callback = callback) ?: error("Callback handler not set")
    }

    fun isPermissionGranted(permission: String): Boolean {
        return listener?.isPermissionGranted(permission = permission) == true
    }

}

interface PermissionResultCallback {
    fun onPermissionGranted()
    fun onPermissionDenied()
}

expect interface PermissionsListener {
    fun requestPermission(permission: String, callback: PermissionResultCallback)
    fun isPermissionGranted(permission: String): Boolean
}
