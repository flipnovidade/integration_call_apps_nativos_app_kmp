package br.com.kmp.demo.demo

import android.app.Application
import br.com.kmp.demo.demo.di.KoinInit

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInit(androidContext = this@MyApplication)
    }
}