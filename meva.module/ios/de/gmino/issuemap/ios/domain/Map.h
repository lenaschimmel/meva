//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/src/de/gmino/issuemap/ios/domain/Map.java
//
//  Created by lena on 24.06.13.
//

@class DeGminoGeobaseIosDomainImageUrl;
@class DeGminoGeobaseIosDomainLatLon;

#import "JreEmulation.h"
#import "de/gmino/issuemap/ios/domain/gen/MapGen.h"

@interface DeGminoIssuemapIosDomainMap : DeGminoIssuemapIosDomainGenMapGen {
}

- (id)initWithLongInt:(long long int)id_;
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
         withNSString:(NSString *)mapTyp;
@end