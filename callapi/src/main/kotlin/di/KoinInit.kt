package org.example.di

import org.koin.core.context.GlobalContext.startKoin

fun initKoin() {
    startKoin {
        modules(sharedModule)
    }
}