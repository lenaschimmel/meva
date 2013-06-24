//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/src/de/gmino/issuemap/ios/domain/Map.java
//
//  Created by lena on 24.06.13.
//

#import "de/gmino/geobase/ios/domain/ImageUrl.h"
#import "de/gmino/geobase/ios/domain/LatLon.h"
#import "de/gmino/issuemap/ios/domain/Map.h"

@implementation DeGminoIssuemapIosDomainMap

- (id)initWithLongInt:(long long int)id_ {
  return [super initWithLongInt:id_];
}

- (id)initWithLongInt:(long long int)id_
             withBOOL:(BOOL)ready
         withNSString:(NSString *)title
         withNSString:(NSString *)description_
         withNSString:(NSString *)subdomain
         withNSString:(NSString *)color
         withNSString:(NSString *)city
withDeGminoGeobaseIosDomainLatLon:(DeGminoGeobaseIosDomainLatLon *)initLocation
              withInt:(int)initZoomlevel
         withNSString:(NSString *)layer
         withNSString:(NSString *)headerText
withDeGminoGeobaseIosDomainImageUrl:(DeGminoGeobaseIosDomainImageUrl *)logo
         withNSString:(NSString *)infoText
         withNSString:(NSString *)mapTyp {
  if ((self = [super initWithLongInt:id_ withBOOL:ready withNSString:title withNSString:description_ withNSString:subdomain withNSString:color withNSString:city withDeGminoGeobaseIosDomainLatLon:(DeGminoGeobaseIosDomainLatLon *) initLocation withInt:initZoomlevel withNSString:layer withNSString:headerText withDeGminoGeobaseIosDomainImageUrl:(DeGminoGeobaseIosDomainImageUrl *) logo withNSString:infoText withNSString:mapTyp])) {
    self.ready = YES;
  }
  return self;
}

@end
