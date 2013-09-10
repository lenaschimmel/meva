//
//  AppDelegate.h
//  BasisApp
//
//  Created by Tillmann Heigel on 02.09.13.
//  Copyright (c) 2013 Greenmobile Innovations. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "NavigationViewController.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) NavigationViewController *navController;
@property (strong, nonatomic) UIWindow *window;

@end
