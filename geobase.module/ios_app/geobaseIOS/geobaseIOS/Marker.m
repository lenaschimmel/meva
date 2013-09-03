//
//  Marker.m
//  geobaseIOS
//
//  Created by Tillmann Heigel on 22.06.13.
//  Copyright (c) 2013 Greenmobile Innovations. All rights reserved.
//

#import "Marker.h"

@implementation Marker

- (id)initWithCoordinate:(CLLocationCoordinate2D *)coordinateParam {
	self = [self init];
	
	if (self != nil) {
		_coordinate = *coordinateParam;
		_name = @"new Marker";
	}
    
	return self;
}

@end
