package br.com.kmp.demo.demo.setpref

import kotlinx.cinterop.*

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SettingsApp: SecureSettings {
//    private val userDefaults = NSUserDefaults.standardUserDefaults()
    val keychainSettings: KeychainSettings = KeychainSettings()

    @OptIn(ExperimentalForeignApi::class)
    actual override fun putString(key: String, value: String) {
//        userDefaults.setObject(value = value, forKey = key)
        keychainSettings.putString(key, value)
    }

    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    actual override fun getString(key: String): String? {
//        return userDefaults.stringForKey(defaultName = key)
        return keychainSettings.getString(key, "empty")
    }

}