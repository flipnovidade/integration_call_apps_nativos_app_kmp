@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package br.com.kmp.demo.demo.permissions

import br.com.kmp.demo.demo.di.koinInstance
import kotlinx.coroutines.GlobalScope
import org.koin.compose.getKoin
import org.koin.core.Koin
import kotlin.coroutines.EmptyCoroutineContext.get
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "PermissionRequestProtocol")
actual interface PermissionsListener {
    actual fun requestPermission(permission: String, callback: PermissionResultCallback)
    actual fun isPermissionGranted(permission: String): Boolean
    actual fun getListContacts(permission: String): List<String>
}

@Suppress("unused")
fun registerPermissionHandler(listener: PermissionsListener){
    koinInstance.koin.get<PermissionRequestMyApp>().setListener(listener)
}