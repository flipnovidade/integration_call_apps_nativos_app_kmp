package br.com.kmp.demo.demo.di

import br.com.kmp.demo.demo.di.repository.CatsRepository
import br.com.kmp.demo.demo.di.usecase.CatsUseCase
import br.com.kmp.demo.demo.firebase.FirebaseRemoteConfigs
import br.com.kmp.demo.demo.firebase.FirebaseRemoteConfigsBridge
import br.com.kmp.demo.demo.network.createHttpClientFactory
import br.com.kmp.demo.demo.permissions.PermissionRequestMyApp
import br.com.kmp.demo.demo.repository.CatRepositoryImple
import br.com.kmp.demo.demo.ui.Routes.LISTCATSCREEN
import br.com.kmp.demo.demo.ui.components.KmpLogger
import br.com.kmp.demo.demo.ui.viewmodel.ListItemScreenViewModel
import br.com.kmp.demo.demo.ui.viewmodel.MainScreenViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun sharedModules() = module {
//    single { createHttpClientFactory() }
//    single<CatsRepository> { CatRepositoryImple(get()) }
//    single { CatsUseCase(get()) }
//    factory { PermissionRequestMyApp() }
}