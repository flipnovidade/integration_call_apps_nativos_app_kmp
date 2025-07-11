package br.com.kmp.demo.demo.ui.components

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class ImageLoader(private val httpClient: HttpClient) {

    suspend fun loadImageByUrl(
        url: String = "https://4.bp.blogspot.com/-I6KBH1NIJGk/XIccojn6YII/AAAAAAAADZ8/BeJ6uuutN5YoQe6Zboig_q5djnXS3hVpgCLcBGAs/s1600/Firebase%2BRealtime%2BDatabase%2B%25281-%2BIcon%252C%2BLight%2529.png"
    ): ByteArray {
        try {
            val response: HttpResponse = httpClient.get(urlString = url)
            return response.body()
        }catch (e: Exception) {
            return ByteArray(0)
        }
    }

}