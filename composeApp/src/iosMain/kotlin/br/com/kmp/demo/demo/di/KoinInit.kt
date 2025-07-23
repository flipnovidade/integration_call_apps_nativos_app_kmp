package br.com.kmp.demo.demo.di

import br.com.kmp.demo.demo.firebase.remoteconfig.FirebaseRemoteConfigs
import br.com.kmp.demo.demo.firebase.realtimedatabase.FirebaseRealTimeDataBase
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExportObjCClass
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import kotlin.experimental.ExperimentalObjCName

lateinit var koinInstance: KoinApplication
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalObjCName::class, BetaInteropApi::class)
@ObjCName(name = "KoinInit", exact = true)
@ExportObjCClass
actual class KoinInit() {

    fun initKoin(delegateFirebaseRemoteConfigs: FirebaseRemoteConfigs,
                 delegateFirebaseRealTimeDataBase: FirebaseRealTimeDataBase) {

        val modules = sharedModules() + moduleIos(delegateFirebaseRemoteConfigs, delegateFirebaseRealTimeDataBase)
        startKoin {
            modules(modules = modules)
        }.also {
            koinInstance = it
        }

    }

}
