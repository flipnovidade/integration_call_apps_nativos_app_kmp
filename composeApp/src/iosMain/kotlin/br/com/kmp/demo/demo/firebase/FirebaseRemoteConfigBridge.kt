package br.com.kmp.demo.demo.firebase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class FirebaseRemoteConfigsBridge(private val delegateFirebaseRemoteConfigs: FirebaseRemoteConfigs) : FirebaseRemoteConfigs {

    actual override fun fetchAndActivateFirebaseRemoteConfigs(fetchIntervalInSeconds: Double) {
        delegateFirebaseRemoteConfigs.fetchAndActivateFirebaseRemoteConfigs(fetchIntervalInSeconds)
    }

    actual override fun getRemoteConfigString(key: String): String? {
        return delegateFirebaseRemoteConfigs.getRemoteConfigString(key = key)
    }

}


