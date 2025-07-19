package br.com.kmp.demo.demo.setpref

import br.com.kmp.demo.demo.KMPContext


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SettingsApp {
    fun putString(key: String, value: String)
    fun getString(key: String): String?
}

interface SecureSettings {
    fun putString(key: String, value: String)
    fun getString(key: String): String?
}