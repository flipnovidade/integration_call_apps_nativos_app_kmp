package org.example

import br.com.api.call.shared.di.initKoin
import br.com.api.call.shared.usecase.CatsUseCase
import kotlin.test.BeforeTest
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue
import org.koin.core.context.GlobalContext

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