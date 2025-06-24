package br.com.kmp.demo.demo.di.repository

import br.com.kmp.demo.demo.model.Cat

interface CatsRepository {

    suspend fun getAllCats(): List<Cat>
    suspend fun getCatById(id: Int): Cat?
    suspend fun getCatsByIds(ids: String): List<Cat>
    suspend fun updateCat(cat: Cat)
    suspend fun deleteCatById(id: Int)
    suspend fun addCat(cat: Cat)

}