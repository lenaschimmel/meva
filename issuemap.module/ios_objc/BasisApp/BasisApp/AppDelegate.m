//
//  AppDelegate.m
//  BasisApp
//
//  Created by Tillmann Heigel on 02.09.13.
//  Copyright (c) 2013 Greenmobile Innovations. All rights reserved.
//

#import "de/gmino/meva/shared/EntityFactory.h"
#import "de/gmino/issuemap/ios/EntityFactoryImpl.h"
#import "de/gmino/meva/shared/Util.h"
#import "de/gmino/meva/ios/UtilIos.h"
#import "de/gmino/meva/ios/request/NetworkRequestsImplAsyncJson.h"
#import "de/gmino/meva/shared/request/Requests.h"
#import "Request.h"
#import "AppDelegate.h"
#import "StartViewController.h"
#import "de/gmino/issuemap/shared/domain/Poi.h"
#import "java/util/Collection.h"
#import "java/util/Iterator.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    StartViewController *startViewController = [[StartViewController alloc] init];
    _navController = [[NavigationViewController alloc] initWithRootViewController:startViewController];

   [MESEntityFactory setImplementationsWithMESEntityFactoryInterface:    [[IMEntityFactoryImpl alloc] init]];
    
    [MESUtil setImpl:[[MEUtilIos alloc]init]];
    
    [MESRequests setImplementationWithMESNetworkRequests:[[DeGminoMevaIosRequestNetworkRequestsImplAsyncJson alloc] initWithNSString:@"http://ios.geoengine.de/"]];
    
    [MESRequests getNewIdsWithMESTypeName:[IMSPoi type] withInt:1
                   withMESRequestListener:(MESRequestListener*)self];

    

    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.
    self.window.backgroundColor = [UIColor whiteColor];
    self.window.rootViewController = _navController;
    [self.window makeKeyAndVisible];
    return YES;
}

- (void)onFinishedWithJavaUtilCollection:(id<JavaUtilCollection>) ids
{
    NSLog(@"Request finished: %@",[[[ids iterator] next] description]);
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
