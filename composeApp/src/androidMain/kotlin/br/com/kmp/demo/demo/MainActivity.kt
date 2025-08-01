package br.com.kmp.demo.demo

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import br.com.kmp.demo.demo.contact.retrieveAllContacts
import br.com.kmp.demo.demo.location.delegate.BluetoothPermissionDelegate
import br.com.kmp.demo.demo.permissions.PermissionRequestMyApp
import br.com.kmp.demo.demo.permissions.PermissionResultCallback
import br.com.kmp.demo.demo.permissions.PermissionsListener
import org.koin.core.context.GlobalContext

class MainActivity : ComponentActivity(), PermissionsListener {

    private var contactPermissionResultCallback: PermissionResultCallback? = null

    private val requestContactPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                contactPermissionResultCallback?.onPermissionGranted()
            } else {
                contactPermissionResultCallback?.onPermissionDenied()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }

        GlobalContext.get().get<PermissionRequestMyApp>().setListener(this)

    }

    override fun requestPermission(permission: String, callback: PermissionResultCallback) {
        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                callback.onPermissionGranted()
            }

            shouldShowRequestPermissionRationale(permission) -> {
                callback.onPermissionDenied()
            }

            else -> {
                contactPermissionResultCallback = callback
                requestContactPermissionLauncher.launch(permission)
            }
        }
    }

    override fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
                this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresPermission(Manifest.permission.READ_CONTACTS)
    override fun getListContacts(permission: String): List<String> {

        if (!isPermissionGranted(permission)) {
            return listOf("DONT HAVE PERMISSION")
        }

        return retrieveAllContacts(
            limit = 50,
            offset = 0
        )

    }

}
