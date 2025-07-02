
import FirebaseRemoteConfig
import ComposeApp


@objc public class SwiftFirebaseRemoteConfig: NSObject, FirebaseRemoteConfigs {
    let remoteConfig = RemoteConfig.remoteConfig()
    
    @objc public func fetchAndActivateFirebaseRemoteConfigs(fetchIntervalInSeconds: Double) {
        remoteConfig.configSettings.minimumFetchInterval = fetchIntervalInSeconds
        remoteConfig.fetchAndActivate()
    }
    
    @objc public func getRemoteConfigString(key: String) -> String? {
        return remoteConfig.configValue(forKey: key).stringValue as String?
    }
    
}
