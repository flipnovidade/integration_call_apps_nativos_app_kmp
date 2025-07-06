import SwiftUI
import FirebaseCore
import ComposeApp

@main
struct iOSApp: App {

    init() {
        FirebaseApp.configure()
        
        let swiftFirebaseRemoteConfig = SwiftFirebaseRemoteConfig()
        let swiftFirebaseDataBaseRealTime = SwiftFirebaseDataBaseRealTime()
        ComposeApp.KoinInit().doInitKoin(
            delegateFirebaseRemoteConfigs: swiftFirebaseRemoteConfig,
            delegateFirebaseRealTimeDataBase: swiftFirebaseDataBaseRealTime
        )
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }

}
