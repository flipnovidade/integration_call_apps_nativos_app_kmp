package br.com.kmp.demo.demo.firebase.remoteconfig

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class FirebaseRemoteConfigsBridge : FirebaseRemoteConfigs {
    override fun fetchAndActivateFirebaseRemoteConfigs(fetchIntervalInSeconds: Double)
    override fun getRemoteConfigString(key: String): String?
}