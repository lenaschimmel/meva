//
//  Marker.h
//  geobaseIOS
//
//  Created by Tillmann Heigel on 22.06.13.
//  Copyright (c) 2013 Greenmobile Innovations. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <MapKit/MapKit.h>

@interface Marker : NSObject <MKAnnotation>

@property NSString *name;
@property CLLocationCoordinate2D coordinate;
@property NSString *logoUrl;

- (id)initWithCoordinate:(CLLocationCoordinate2D *)coordinateParam;

//custom callout
//http://stackoverflow.com/questions/6945045/custom-callout-view-for-mkannotation

@end
