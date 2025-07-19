package br.com.kmp.demo.demo.setpref

import kotlinx.cinterop.*
import platform.CoreFoundation.*
import platform.Foundation.*
import platform.Security.*
import platform.CoreFoundation.CFTypeRefVar
import platform.Foundation.*
import platform.Security.SecItemCopyMatching

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SettingsApp: SecureSettings {
    private val userDefaults = NSUserDefaults.standardUserDefaults()

    actual override fun putString(key: String, value: String) {
        userDefaults.setObject(value = value, forKey = key)
    }

    actual override fun getString(key: String): String? {
        return userDefaults.stringForKey(defaultName = key)
    }

}
