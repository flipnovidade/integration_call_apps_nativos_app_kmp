package br.com.api.call.shared.di.repository

import br.com.api.call.shared.model.Cat

interface CatsRepository {

    suspend fun getAllCats(): List<Cat>
    suspend fun getCatById(id: Int): Cat?
    suspend fun getCatsByIds(ids: String): List<Cat>
    suspend fun updateCat(cat: Cat)
    suspend fun deleteCatById(id: Int)
    suspend fun addCat(cat: Cat)

}