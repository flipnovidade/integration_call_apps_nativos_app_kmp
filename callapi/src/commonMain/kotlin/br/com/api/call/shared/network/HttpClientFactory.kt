package br.com.api.call.shared.network

import io.ktor.client.*

expect fun createHttpClientFactory(): HttpClient