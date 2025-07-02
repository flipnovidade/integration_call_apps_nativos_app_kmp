package br.com.kmp.demo.demo.di

import br.com.kmp.remoteconfig.SwiftFirebaseRemoteConfig
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExportObjCClass
import org.koin.core.context.startKoin
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class, BetaInteropApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@ObjCName(name = "KoinInit", exact = true)
@ExportObjCClass
actual class KoinInit(@ObjCName("delegate") delegate: SwiftFirebaseRemoteConfig) {

    init {
        doInitKoin(delegate = delegate)
    }

    fun initKoin(delegate: SwiftFirebaseRemoteConfig) {
        startKoin {
            modules(modules = moduleIos(delegate))
        }
    }

    fun doInitKoin(delegate: SwiftFirebaseRemoteConfig) = initKoin(delegate)
}

