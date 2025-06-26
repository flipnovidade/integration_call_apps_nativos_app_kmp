package br.com.kmp.demo.demo.ui.components

expect object KmpLogger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}