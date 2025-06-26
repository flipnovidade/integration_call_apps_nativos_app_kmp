package br.com.kmp.demo.demo.ui.components

import kotlin.io.println

actual object KmpLogger {
    actual fun d(tag: String, message: String) {
        println("DEBUG: [$tag] $message")
    }

    actual fun e(tag: String, message: String, throwable: Throwable?) {
        println("ERROR: [$tag] $message")
        throwable?.printStackTrace()
    }
}