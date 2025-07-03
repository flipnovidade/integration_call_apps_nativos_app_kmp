//
//  SwiftFirebaseRemoteConfig.h
//  iosApp
//
//  Created by Felippe Ferreira on 26/06/2025.
//

#import <Foundation/Foundation.h>
#import <ComposeApp/ComposeApp.h>

@interface SwiftFirebaseRemoteConfig : NSObject <ComposeApp.FirebaseRemoteConfigs>

- (void)fetchAndActivateFirebaseRemoteConfigsWithFetchIntervalInSeconds:(double)fetchIntervalInSeconds;
- (NSString * _Nullable)getRemoteConfigStringWithKey:(NSString * _Nonnull)key;

@end
