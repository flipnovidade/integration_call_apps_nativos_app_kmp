import SwiftUI
import FirebaseCore
import ComposeApp

@main
struct iOSApp: App {

    init() {
        FirebaseApp.configure()
        
        let swiftFirebaseRemoteConfig = SwiftFirebaseRemoteConfig()
        ComposeApp.KoinInit().doInitKoin(delegate: swiftFirebaseRemoteConfig)
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }

}
