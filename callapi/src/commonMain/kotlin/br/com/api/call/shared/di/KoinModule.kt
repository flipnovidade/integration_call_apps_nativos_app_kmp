package br.com.api.call.shared.di

import br.com.api.call.shared.repository.CatRepositoryImple
import br.com.api.call.shared.di.repository.CatsRepository
import br.com.api.call.shared.network.createHttpClientFactory
import br.com.api.call.shared.usecase.CatsUseCase
import org.koin.dsl.module

val sharedModule = module {
    single { createHttpClientFactory() }
    single<CatsRepository> { CatRepositoryImple(get()) }
    single { CatsUseCase(get()) }
}