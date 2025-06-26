package br.com.kmp.demo.demo

import android.os.Build

class AndroidPlatform : Platform {
//    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val name: String = "android"
}

actual fun getPlatform(): Platform = AndroidPlatform()