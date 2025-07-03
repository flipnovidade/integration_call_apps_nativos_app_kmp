import SwiftUI
import FirebaseCore
import ComposeApp

@main
struct iOSApp: App {

    init() {
        FirebaseApp.configure()
//        ComposeApp.KoinInit.init(delegate: SwiftFirebaseRemoteConfig())
//        ComposeApp.KoinInit()
//        ComposeApp.KoinInit().initKoin(delegate: SwiftFirebaseRemoteConfig())
//        ComposeApp.KoinInit().doInitKoin(delegate: SwiftFirebaseRemoteConfig())
//        ComposeApp.KoinInit().doInitKoinDelegate(SwiftFirebaseRemoteConfig())
//        ComposeApp.KoinInit().doInitKoinDelegate(delegate: SwiftFirebaseRemoteConfig())
//        ComposeApp.KoinInit()
//        ComposeApp.KoinInit(delegate: SwiftFirebaseRemoteConfig())
//        ComposeApp.KoinInit(delegate: SwiftFirebaseRemoteConfig())
//        ComposeApp.KoinInit().doInitKoinDelegate(delegate: SwiftFirebaseRemoteConfig())
//        ComposeApp.KoinInit().doInitKoinDelegate(delegate: SwiftFirebaseRemoteConfig())
        
//        let swiftRemoteConfig = SwiftFirebaseRemoteConfig()
//        let remoteConfigBridge = ComposeAppFirebaseRemoteConfigsBridge(delegate: swiftRemoteConfig)
//
//        let koinInit = ComposeAppKoinInit()
//        koinInit.doInitKoin(delegate: remoteConfigBridge)
        let delegate = SwiftFirebaseRemoteConfig()
        
//       let koin = ComposeApp.KoinInit(delegate: delegate)
//        let koin = KoinInit(delegate: delegate)
//        koin.doInitKoin(delegate: delegate)
        
        
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }

}
