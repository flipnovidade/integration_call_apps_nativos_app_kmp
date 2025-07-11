@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package br.com.kmp.demo.demo.permissions


actual interface PermissionsListener {
    actual fun requestPermission(permission: String, callback: PermissionResultCallback)
    actual fun isPermissionGranted(permission: String): Boolean
}
