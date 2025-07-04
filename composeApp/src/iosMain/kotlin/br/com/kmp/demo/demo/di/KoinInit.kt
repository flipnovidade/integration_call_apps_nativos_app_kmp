package br.com.kmp.demo.demo.di

import br.com.kmp.demo.demo.ui.components.KmpLogger
import br.com.kmp.remoteconfig.SwiftFirebaseRemoteConfig
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExportObjCClass
import org.koin.core.context.startKoin
import kotlin.experimental.ExperimentalObjCName

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalObjCName::class, BetaInteropApi::class)
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

fun someFunctionThatNeedsSwift(isVercade: Boolean) {
    KmpLogger.d("someFunctionThatNeedsSwift", "receiver delegate $isVercade")
}

fun someFunctionThatNeedsSwiftToRead(firebaseRemoteconfig: SwiftFirebaseRemoteConfig) {
    KmpLogger.d("someFunctionThatNeedsSwift", "receiver delegate amem")
    val swiftFirebaseRemoteConfig: SwiftFirebaseRemoteConfig = (firebaseRemoteconfig)
    KoinInit(delegate = swiftFirebaseRemoteConfig)
}


