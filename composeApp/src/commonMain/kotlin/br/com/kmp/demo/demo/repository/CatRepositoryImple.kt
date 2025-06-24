package br.com.kmp.demo.demo.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import br.com.kmp.demo.demo.model.Cat
import br.com.kmp.demo.demo.di.repository.CatsRepository

class CatRepositoryImple(private val client: HttpClient): CatsRepository {

    override suspend fun getCatById(id: Int): Cat? {
        return client.get("https://api.restful-api.dev/objects/$id").body()
    }

    override suspend fun getCatsByIds(ids: String): List<Cat> {
        return client.get("https://api.restful-api.dev/objects?$ids").body()
    }

    override suspend fun getAllCats(): List<Cat> {
        return client.get("https://api.restful-api.dev/objects").body()
    }

    override suspend fun updateCat(cat: Cat) {
        client.put("https://api.restful-api.dev/objects/${cat.id}") {
            contentType(ContentType.Application.Json)
            setBody(cat)
        }
    }

    override suspend fun deleteCatById(id: Int) {
        client.delete("https://api.restful-api.dev/objects/$id")
    }

    override suspend fun addCat(cat: Cat) {
        client.post("https://api.restful-api.dev/objects"){
            contentType(ContentType.Application.Json)
            setBody(cat)
        }
    }

}