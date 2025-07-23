package br.com.kmp.demo.demo.di

import br.com.kmp.demo.demo.KMPContext
import br.com.kmp.demo.demo.repository.ApiSdkCall
import br.com.kmp.demo.demo.di.repository.CatsRepository
import br.com.kmp.demo.demo.di.usecase.CatsUseCase
import br.com.kmp.demo.demo.firebase.remoteconfig.FirebaseRemoteConfigs
import br.com.kmp.demo.demo.firebase.remoteconfig.FirebaseRemoteConfigsBridge
import br.com.kmp.demo.demo.firebase.realtimedatabase.FirebaseDataBaseRealTimeBridge
import br.com.kmp.demo.demo.firebase.realtimedatabase.FirebaseRealTimeDataBase
import br.com.kmp.demo.demo.network.createHttpClientFactory
import br.com.kmp.demo.demo.permissions.PermissionRequestMyApp
import br.com.kmp.demo.demo.repository.CatRepositoryImple
import br.com.kmp.demo.demo.setpref.SecureSettings
import br.com.kmp.demo.demo.setpref.SettingsApp
import br.com.kmp.demo.demo.ui.Routes.LISTCATSCREEN
import br.com.kmp.demo.demo.ui.components.KmpLogger
import br.com.kmp.demo.demo.ui.viewmodel.FirebaseRealTimeDataBaseViewModel
import br.com.kmp.demo.demo.ui.viewmodel.ListItemScreenViewModel
import br.com.kmp.demo.demo.ui.viewmodel.MainScreenViewModel
import br.com.kmp.demo.demo.ui.viewmodel.TakePictureViewModel
import br.com.kmp.demo.demo.ui.viewmodel.PermissionsContactListViewModel
import br.com.kmp.demo.demo.ui.viewmodel.StoreDataViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun moduleIos(delegateFirebaseRemoteConfigs: FirebaseRemoteConfigs,
              delegateFirebaseRealTimeDataBase: FirebaseRealTimeDataBase) = module {
    single { KMPContext() }
    single { createHttpClientFactory() }
    single<CatsRepository> { CatRepositoryImple(get()) }
    single { CatsUseCase(get()) }
    single { ApiSdkCall(get()) }
    single { KmpLogger }

    scope(named(LISTCATSCREEN)) {
        scoped { MainScreenViewModel(get(), get()) }
    }

    single<FirebaseRemoteConfigs> { FirebaseRemoteConfigsBridge(delegateFirebaseRemoteConfigs) }
    factory { ListItemScreenViewModel(get<FirebaseRemoteConfigs>() as FirebaseRemoteConfigsBridge, get(), get()) }

    single<FirebaseRealTimeDataBase> { FirebaseDataBaseRealTimeBridge(delegateFirebaseRealTimeDataBase) }
    factory { FirebaseRealTimeDataBaseViewModel(get<FirebaseRealTimeDataBase>() as FirebaseDataBaseRealTimeBridge, get()) }

    single <PermissionRequestMyApp> { PermissionRequestMyApp() }
    factory { PermissionsContactListViewModel( "CONTATO", get()) }

    single <SecureSettings> { SettingsApp() }
    factory { StoreDataViewModel(get<SecureSettings>() as SettingsApp, get()) }

    single <PermissionRequestMyApp> { PermissionRequestMyApp() }
    factory { TakePictureViewModel("CAMERA", get()) }
}