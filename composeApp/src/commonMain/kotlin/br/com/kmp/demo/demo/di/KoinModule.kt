package br.com.kmp.demo.demo.di

import br.com.kmp.demo.demo.repository.CatRepositoryImple
import br.com.kmp.demo.demo.di.repository.CatsRepository
import br.com.kmp.demo.demo.network.createHttpClientFactory
import br.com.kmp.demo.demo.di.usecase.CatsUseCase
import br.com.kmp.demo.demo.ui.viewmodel.MainScreenViewModel
import org.koin.dsl.module

val sharedModule = module {
    single { createHttpClientFactory() }
    single<CatsRepository> { CatRepositoryImple(get()) }
    single { CatsUseCase(get()) }
    factory { MainScreenViewModel(get()) }
}