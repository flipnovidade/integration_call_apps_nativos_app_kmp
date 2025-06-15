package org.example

import kotlin.test.BeforeTest
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue
import org.example.di.initKoin
import org.koin.core.context.GlobalContext
import org.example.di.usecase.CatsUseCase

class UseCaseTest {

    @BeforeTest
    fun setup() {
        initKoin()
    }

    @Test
    fun testGetCats() = runBlocking {
        val useCase = GlobalContext.get().get<CatsUseCase>()
        val cats = useCase.getAllCats()
        println(cats)
        assertTrue(cats.isNotEmpty(), "Lista de gatos não deve estar vazia")
    }

}