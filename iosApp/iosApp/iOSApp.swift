import SwiftUI
import FirebaseCore
import ComposeApp

@main
struct iOSApp: App {

    init() {
        FirebaseApp.configure()
        
        let swiftFirebaseRemoteConfig = SwiftFirebaseRemoteConfig()
        KoinInitKt.someFunctionThatNeedsSwift(isVercade: true)
        
//        KoinInitKt.someFunctionThatNeedsSwiftToRead(firebaseRemoteconfig: swiftFirebaseRemoteConfig)
//        ComposeApp.KoinInit(delegate: swiftFirebaseRemoteConfig)
//        ComposeApp.KoinInit(delegate: swiftFirebaseRemoteConfig).doKoInitKoin(delegate: swiftFirebaseRemoteConfig)
        
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }

}
