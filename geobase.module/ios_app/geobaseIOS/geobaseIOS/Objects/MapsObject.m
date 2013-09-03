//
//  MapsObject.m
//  geobaseIOS
//
//  Created by Tillmann Heigel on 19.06.13.
//  Copyright (c) 2013 Greenmobile Innovations. All rights reserved.
//

#import "MapsObject.h"

@interface MapsObject () {
    }

@end

@implementation MapsObject



- (id)initWithName:(NSString *)name andLongitude:(double)longitude andLatitude:(double)latitude{
    self = [super init];  
    if (self){
        _name = name;
        _latitude = latitude;
        _longitude = longitude;
        
    }
    return self;
}



@end
