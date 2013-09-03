//
//  MapsObject.h
//  geobaseIOS
//
//  Created by Tillmann Heigel on 19.06.13.
//  Copyright (c) 2013 Greenmobile Innovations. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <MapKit/MapKit.h>

@interface MapsObject : NSObject

@property NSString *name;
@property double longitude;
@property double latitude;
@property NSMutableArray *markers;
@property UIColor *firstColor;
@property UIColor *scndColor;

- (id)initWithName:(NSString *)name andLongitude:(double)longitude andLatitude:(double)latitude;

@end
