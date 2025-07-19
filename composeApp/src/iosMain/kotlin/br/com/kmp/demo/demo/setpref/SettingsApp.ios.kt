package br.com.kmp.demo.demo.setpref

import br.com.kmp.demo.demo.KMPContext
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.refTo
import kotlinx.cinterop.value
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFTypeRefVar
import platform.CoreFoundation.kCFBooleanTrue
import platform.Security.SecItemDelete
import platform.Security.kSecClass
import platform.Security.kSecClassGenericPassword
import platform.Security.*
import platform.Foundation.*
import platform.posix.memcpy

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class)
actual class SettingsApp: SecureSettings {

    override fun putString(key: String, value: String) {
        val keyData = key.encodeToByteArray()
        val valueData = value.encodeToByteArray()

        SecItemDelete(
            mapOf(
                kSecClass to kSecClassGenericPassword,
                kSecAttrAccount to keyData
            ) as CFDictionaryRef
        )

        SecItemAdd(
            mapOf(
                kSecClass to kSecClassGenericPassword,
                kSecAttrAccount to keyData,
                kSecValueData to valueData
            ) as CFDictionaryRef, null
        )
    }

    override fun getString(key: String): String? {
        return memScoped {
            val keyData = key.toNSData()
            val query = mapOf<Any?, Any?>(
                kSecClass to kSecClassGenericPassword,
                kSecAttrAccount to keyData,
                kSecReturnData to kCFBooleanTrue,
                kSecMatchLimit to kSecMatchLimitOne
            ) as CFDictionaryRef?

            val result = alloc<CFTypeRefVar>()
            val status = SecItemCopyMatching(query = query!!, result = result.ptr)
            if (status != errSecSuccess) return@memScoped null

            val nsData = result.value.toString().toNSData()
            val bytes = ByteArray(nsData!!.length.toInt()).also { arr ->
                memScoped {
                    memcpy(arr.refTo(0), nsData.bytes, nsData.length)
                }
            }
            bytes.decodeToString()
        }
    }

}

private fun String.toNSData(): NSData? {
    return (this as NSString).dataUsingEncoding(NSUTF8StringEncoding)!!
}
