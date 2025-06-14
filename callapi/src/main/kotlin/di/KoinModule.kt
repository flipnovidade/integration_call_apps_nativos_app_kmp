package org.example.di

import org.example.data.network.creacteHttpClient
import org.example.data.repository.CatRepositoryImple
import org.example.di.repository.CatsRepository
import org.example.di.usecase.CatsUseCase
import org.koin.dsl.module

val sharedModule = module {
    single { creacteHttpClient() }
    single<CatsRepository> { CatRepositoryImple(get()) }
    single { CatsUseCase(get()) }
}