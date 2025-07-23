package br.com.kmp.demo.demo.firebase.remoteconfig

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class FirebaseRemoteConfigsBridge : FirebaseRemoteConfigs {
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

    actual override fun fetchAndActivateFirebaseRemoteConfigs(fetchIntervalInSeconds: Double) {
        val configSettings = remoteConfigSettings {
            this.fetchTimeoutInSeconds = fetchIntervalInSeconds.toLong()
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
    }

    actual override fun getRemoteConfigString(key: String): String? {
        return remoteConfig.getString(key)
    }

}