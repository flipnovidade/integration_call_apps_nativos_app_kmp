package br.com.kmp.demo.demo

import br.com.kmp.demo.demo.di.sharedModule
import br.com.kmp.demo.demo.model.Cat
import br.com.kmp.demo.demo.di.usecase.CatsUseCase
import org.koin.core.KoinApplication
import org.koin.core.context.stopKoin
import org.koin.core.context.startKoin

class ApiSdkCall {

    private val useCase: CatsUseCase

    init {
        val koinApp: KoinApplication = startKoin {
            modules(sharedModule)
        }
        val koin = koinApp.koin
        useCase = koin.get<CatsUseCase>()
    }

    suspend fun getCats(): List<Cat> {
        return useCase.getAllCats()
    }

    suspend fun getCat(catId: Int): Cat? {
        return useCase.getCatById(catId = catId)
    }

    suspend fun getCatsByIds(catsId: String): List<Cat> {
        return useCase.getCatsByIds(catId = catsId)
    }

    suspend fun updateCat(cat: Cat){
        useCase.updateCat(cat)
    }

    suspend fun deleteCat(catId: Int) {
        useCase.deleteCat(catId = catId)
    }

    suspend fun addCat(cat: Cat){
        useCase.addCat(cat = cat)
    }

    fun stopKoinApp() {
        stopKoin()
    }

}