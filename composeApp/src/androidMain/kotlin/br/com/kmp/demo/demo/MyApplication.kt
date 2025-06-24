package br.com.kmp.demo.demo

import android.app.Application
import br.com.kmp.demo.demo.di.sharedModule
import org.koin.android.ext.koin.androidContext

import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            androidContext(this@MyApplication)
//            modules(sharedModule)
//        }
    }
}