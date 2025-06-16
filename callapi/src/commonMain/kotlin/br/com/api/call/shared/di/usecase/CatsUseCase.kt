package br.com.api.call.shared.usecase

import br.com.api.call.shared.model.Cat
import br.com.api.call.shared.di.repository.CatsRepository

class CatsUseCase(private val catsRepository: CatsRepository) {

    suspend fun getCatById(catId: Int): Cat?{
        return catsRepository.getCatById(catId)
    }

    suspend fun getCatsByIds(catId: String): List<Cat>{
        return catsRepository.getCatsByIds( ids = catId )
    }

    suspend fun getAllCats(): List<Cat>{
        return catsRepository.getAllCats()
    }

    suspend fun updateCat(cat: Cat){
        catsRepository.updateCat(cat)
    }

    suspend fun deleteCat(catId: Int){
        catsRepository.deleteCatById(catId)
    }

    suspend fun addCat(cat: Cat){
        catsRepository.addCat(cat)
    }


}