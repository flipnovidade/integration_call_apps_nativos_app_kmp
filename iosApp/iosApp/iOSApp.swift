import SwiftUI
import Photos
import AVFoundation
import Contacts
import FirebaseCore
import ComposeApp

class AppDelegate: NSObject, UIApplicationDelegate, PermissionRequestProtocol {

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        
        FirebaseApp.configure()
        
        let swiftFirebaseRemoteConfig = SwiftFirebaseRemoteConfig()
        let swiftFirebaseDataBaseRealTime = SwiftFirebaseDataBaseRealTime()
        ComposeApp.KoinInit().doInitKoin(
            delegateFirebaseRemoteConfigs: swiftFirebaseRemoteConfig,
            delegateFirebaseRealTimeDataBase: swiftFirebaseDataBaseRealTime
        )
        
        PermissionRequestMyApp_iosKt.registerPermissionHandler(listener: self)
        
        return true
    }
    
    func requestPermission(permission: String, callback: any PermissionResultCallback) {
        
        switch permission.uppercased() {
        case "CAMERA":
            requestCameraAndPhotoLibrary(callback: callback)
            
        case "CONTATO":
            requestContacts(callback: callback)
            
        default:
            callback.onPermissionDenied()
        }
    }
    
    func requestContacts(callback: any PermissionResultCallback) {
        let store = CNContactStore()
        switch CNContactStore.authorizationStatus(for: .contacts) {
        case .notDetermined:
            store.requestAccess(for: .contacts) { granted, error in
                DispatchQueue.main.async {
                    if granted {
                        callback.onPermissionGranted()
                    } else {
                        callback.onPermissionDenied()
                    }
                }
            }
        case .denied:
            DispatchQueue.main.async {
                callback.onPermissionDenied()
            }
        case .restricted:
            DispatchQueue.main.async {
                callback.onPermissionDenied()
            }
        case .authorized:
            DispatchQueue.main.async {
                callback.onPermissionGranted()
            }
        case .limited:
            DispatchQueue.main.async {
                callback.onPermissionDenied()
            }
        @unknown default:
            fatalError("Unknown authorization status")
        }
    }
    
    func requestCameraAndPhotoLibrary(callback: PermissionResultCallback) {
        let cameraStatus = AVCaptureDevice.authorizationStatus(for: .video)
        let photoStatus = PHPhotoLibrary.authorizationStatus()
        
        var cameraGranted = false
        var photoGranted = false

        let checkCompletion: () -> Void = {
            if cameraGranted && photoGranted {
                DispatchQueue.main.async {
                    callback.onPermissionGranted()
                }
            } else if cameraStatus == .denied || photoStatus == .denied {
                DispatchQueue.main.async {
                    callback.onPermissionDenied()
                }
            }
        }

        // Câmera
        if cameraStatus == .notDetermined {
            AVCaptureDevice.requestAccess(for: .video) { granted in
                cameraGranted = granted
                checkCompletion()
            }
        } else {
            cameraGranted = (cameraStatus == .authorized)
        }

        // Galeria
        if photoStatus == .notDetermined {
            PHPhotoLibrary.requestAuthorization { status in
                photoGranted = (status == .authorized || status == .limited)
                checkCompletion()
            }
        } else {
            photoGranted = (photoStatus == .authorized || photoStatus == .limited)
        }

        // Caso ambos já estavam definidos
        if cameraStatus != .notDetermined && photoStatus != .notDetermined {
            checkCompletion()
        }
    }

    func isPermissionGranted(permission: String) -> Bool {
        switch permission.uppercased() {
        case "CAMERA":
            let cameraStatus = AVCaptureDevice.authorizationStatus(for: .video)
            let photoStatus = PHPhotoLibrary.authorizationStatus()
            
            let cameraGranted = (cameraStatus == .authorized)
            let photoGranted = (photoStatus == .authorized || photoStatus == .limited)
            
            return cameraGranted && photoGranted
            
        case "CONTATO":
            let contactStatus = CNContactStore.authorizationStatus(for: .contacts)
            return contactStatus == .authorized
            
        default:
            return false
        }
    }

}


@main
struct iOSApp: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }

}
