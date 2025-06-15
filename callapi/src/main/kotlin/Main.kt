package org.example

import kotlinx.coroutines.runBlocking
import org.example.di.initKoin
import org.example.di.repository.CatsRepository
import org.example.di.usecase.CatsUseCase
import org.koin.core.context.GlobalContext
import org.koin.java.KoinJavaComponent.inject

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(): kotlin.Unit = runBlocking {
    initKoin()

    //TODO Example How TO USE
    val useCase = GlobalContext.get().get<CatsUseCase>()

    println(useCase.getAllCats());
//    println(useCase.getCatById(catId = 1));


    
}