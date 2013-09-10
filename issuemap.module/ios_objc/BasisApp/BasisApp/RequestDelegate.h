//
//  Request.h
//  BasisApp
//
//  Created by Tillmann Heigel on 03.09.13.
//  Copyright (c) 2013 Greenmobile Innovations. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "de/gmino/meva/ios/request/RequestCallback.h"

@interface RequestDelegate : NSObject
-(id)initWithMERequestCallback:(id<DeGminoMevaIosRequestRequestCallback>) cb;
@end
