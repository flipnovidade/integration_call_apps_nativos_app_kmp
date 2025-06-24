package br.com.kmp.demo.demo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform