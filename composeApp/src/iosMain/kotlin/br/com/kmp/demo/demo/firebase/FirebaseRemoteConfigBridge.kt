package br.com.kmp.demo.demo.firebase

import br.com.kmp.remoteconfig.SwiftFirebaseRemoteConfig

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class FirebaseRemoteConfigsBridge(private val delegate: SwiftFirebaseRemoteConfig) : FirebaseRemoteConfigs {

    override fun fetchAndActivateFirebaseRemoteConfigs(fetchIntervalInSeconds: Double) {
        delegate.fetchAndActivateFirebaseRemoteConfigsWithFetchIntervalInSeconds(fetchIntervalInSeconds = fetchIntervalInSeconds)
    }

    override fun getRemoteConfigString(key: String): String? {
        return delegate.getRemoteConfigStringWithKey(key = key)?.toString()
    }

}


