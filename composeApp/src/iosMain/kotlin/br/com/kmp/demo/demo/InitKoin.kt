package br.com.kmp.demo.demo

import br.com.kmp.demo.demo.di.sharedModule
import org.koin.core.context.startKoin
import kotlin.experimental.ExperimentalObjCName
import kotlin.jvm.JvmName


fun initKoinIos() = startKoin {
    modules(sharedModule)
}