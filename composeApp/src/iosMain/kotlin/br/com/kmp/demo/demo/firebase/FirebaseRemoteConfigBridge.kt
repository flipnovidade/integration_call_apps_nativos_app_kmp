package br.com.kmp.demo.demo.firebase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class FirebaseRemoteConfigsBridge(private val delegate: FirebaseRemoteConfigs) : FirebaseRemoteConfigs {

    actual override fun fetchAndActivateFirebaseRemoteConfigs(fetchIntervalInSeconds: Double) {
        delegate.fetchAndActivateFirebaseRemoteConfigs(fetchIntervalInSeconds)
    }

    actual override fun getRemoteConfigString(key: String): String? {
        return delegate.getRemoteConfigString(key = key)
    }

}


