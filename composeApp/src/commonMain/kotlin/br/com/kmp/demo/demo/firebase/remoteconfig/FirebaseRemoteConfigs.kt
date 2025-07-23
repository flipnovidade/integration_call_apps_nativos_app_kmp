package br.com.kmp.demo.demo.firebase.remoteconfig

interface FirebaseRemoteConfigs{
    fun fetchAndActivateFirebaseRemoteConfigs(fetchIntervalInSeconds: Double)
    fun getRemoteConfigString(key: String): String?
}
