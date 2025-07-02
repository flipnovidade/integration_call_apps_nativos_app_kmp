package br.com.kmp.demo.demo.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KoinInit() {

    constructor(androidContext: Context) : this() {
        initKoin(androidContext)
    }

    fun initKoin(androidContext: Context) {
        startKoin {
            androidLogger()
            androidContext(androidContext = androidContext)
            modules(modules = moduleAndroid())
        }
    }
}
