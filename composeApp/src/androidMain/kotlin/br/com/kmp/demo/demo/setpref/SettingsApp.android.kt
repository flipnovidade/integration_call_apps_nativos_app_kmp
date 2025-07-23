package br.com.kmp.demo.demo.setpref

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SettingsApp(private val context: Context) : SecureSettings {

    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    val spec = KeyGenParameterSpec.Builder(
        masterKeyAlias,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(256)
        .build()

    val masterKey = MasterKey.Builder(context)
        .setKeyGenParameterSpec(spec)
        .build()

    val encryptedPrefs = EncryptedSharedPreferences.create(
        context,
        "my-app-teach",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    actual override fun putString(key: String, value: String) {
        encryptedPrefs.edit { putString(key, value) }
    }

    actual override fun getString(key: String): String? {
        return encryptedPrefs.getString(key, "empty")
    }

}
