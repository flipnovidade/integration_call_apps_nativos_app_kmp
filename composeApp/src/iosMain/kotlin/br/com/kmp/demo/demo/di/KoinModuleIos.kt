package br.com.kmp.demo.demo.di

import br.com.kmp.demo.demo.KMPContext
import br.com.kmp.demo.demo.repository.ApiSdkCall
import br.com.kmp.demo.demo.di.repository.CatsRepository
import br.com.kmp.demo.demo.di.usecase.CatsUseCase
import br.com.kmp.demo.demo.firebase.remoteconfig.FirebaseRemoteConfigs
import br.com.kmp.demo.demo.firebase.remoteconfig.FirebaseRemoteConfigsBridge
import br.com.kmp.demo.demo.firebase.realtimedatabase.FirebaseDataBaseRealTimeBridge
import br.com.kmp.demo.demo.firebase.realtimedatabase.FirebaseRealTimeDataBase
import br.com.kmp.demo.demo.location.delegate.BluetoothPermissionDelegate
import br.com.kmp.demo.demo.location.delegate.BluetoothServicePermissionDelegate
import br.com.kmp.demo.demo.location.delegate.LocationBackgroundPermissionDelegate
import br.com.kmp.demo.demo.location.delegate.LocationForegroundPermissionDelegate
import br.com.kmp.demo.demo.location.delegate.LocationServicePermissionDelegate
import br.com.kmp.demo.demo.location.delegate.PermissionDelegate
import br.com.kmp.demo.demo.location.model.Permission
import br.com.kmp.demo.demo.location.provider.LocationProvider
import br.com.kmp.demo.demo.location.provider.IOSLocationProvider
import br.com.kmp.demo.demo.location.service.PermissionsService
import br.com.kmp.demo.demo.location.service.PermissionsServiceImpl
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
import br.com.kmp.demo.demo.ui.viewmodel.LocationScreenViewModel
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

    single<PermissionDelegate>(named(Permission.BLUETOOTH_SERVICE_ON.name)) {
        BluetoothServicePermissionDelegate()
    }

    single<PermissionDelegate>(named(Permission.BLUETOOTH.name)) {
        BluetoothPermissionDelegate()
    }

    single<PermissionDelegate>(named(Permission.LOCATION_SERVICE_ON.name)) {
        LocationServicePermissionDelegate()
    }

    single<PermissionDelegate>(named(Permission.LOCATION_FOREGROUND.name)) {
        LocationForegroundPermissionDelegate()
    }

    single<PermissionDelegate>(named(Permission.LOCATION_BACKGROUND.name)) {
        LocationBackgroundPermissionDelegate(
            locationForegroundPermissionDelegate = get(named(Permission.LOCATION_FOREGROUND.name)),
        )
    }

    single<PermissionsService> { PermissionsServiceImpl() }
    single<LocationProvider>(named("locationProvider")){
        IOSLocationProvider()
    }
    factory {
        LocationScreenViewModel(
            get(),
            get(named("locationProvider"))
        )
    }

}