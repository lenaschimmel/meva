//
//  Parser.m
//  geobaseIOS
//
//  Created by Tillmann Heigel on 19.06.13.
//  Copyright (c) 2013 Greenmobile Innovations. All rights reserved.
//

#import "MapsObject.h"
#import "Parser.h"

@implementation Parser

-(NSArray*)getAllMapsObjects{
    
    MapsObject *mapsObject_1 = [[MapsObject alloc] initWithName:@"Piraten_Braunschweig"andLongitude:54.123 andLatitude:9.32];
    MapsObject *mapsObject_2 = [[MapsObject alloc] initWithName:@"FDP_Marburg"andLongitude:52.123 andLatitude:12.324];
    MapsObject *mapsObject_3 = [[MapsObject alloc] initWithName:@"SPD_Fulda"andLongitude:50.123 andLatitude:7.32];
    
    [mapsObject_1 setFirstColor:[UIColor orangeColor]];
    [mapsObject_1 setScndColor:[UIColor black50PercentColor]];
    
    
    [mapsObject_2 setFirstColor:[UIColor yellowColor]];
    [mapsObject_2 setScndColor:[UIColor black50PercentColor]];

    
    [mapsObject_3 setFirstColor:[UIColor tomatoColor]];
    [mapsObject_3 setScndColor:[UIColor black50PercentColor]];

    
    
    return [[NSArray alloc]initWithObjects:mapsObject_1, mapsObject_2, mapsObject_3, nil];
}

@end
