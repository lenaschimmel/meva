//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/cpy/de/gmino/geobase/ios/domain/GeoObject.java
//
//  Created by lena on 24.06.13.
//

@class DeGminoGeobaseIosDomainImageUrl;
@class DeGminoGeobaseIosDomainLatLon;

#import "JreEmulation.h"
#import "de/gmino/geobase/ios/domain/gen/GeoObjectGen.h"

@interface DeGminoGeobaseIosDomainGeoObject : DeGminoGeobaseIosDomainGenGeoObjectGen {
}

- (id)initWithLongInt:(long long int)id_;
- (id)initWithLongInt:(long long int)id_
             withBOOL:(BOOL)ready
withDeGminoGeobaseIosDomainLatLon:(DeGminoGeobaseIosDomainLatLon *)location
         withNSString:(NSString *)title
         withNSString:(NSString *)description_
withDeGminoGeobaseIosDomainImageUrl:(DeGminoGeobaseIosDomainImageUrl *)markerImage;
@end