package br.com.kmp.demo.demo.di

import br.com.kmp.demo.demo.firebase.FirebaseRemoteConfigs
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExportObjCClass
import org.koin.core.context.startKoin
import kotlin.experimental.ExperimentalObjCName

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalObjCName::class, BetaInteropApi::class)
@ObjCName(name = "KoinInit", exact = true)
@ExportObjCClass
actual class KoinInit() {

    fun initKoin(delegate: FirebaseRemoteConfigs) {
        startKoin {
            modules(modules = moduleIos(delegate))
        }
    }

}
