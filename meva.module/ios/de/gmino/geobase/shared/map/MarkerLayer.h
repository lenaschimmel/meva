//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/cpy/de/gmino/geobase/shared/map/MarkerLayer.java
//
//  Created by lena on 24.06.13.
//

@class DeGminoGeobaseSharedDomainImageUrl;
@class DeGminoGeobaseSharedDomainLatLon;
@protocol DeGminoGeobaseSharedMapMarker;

#import "JreEmulation.h"
#import "de/gmino/geobase/shared/map/MapLayer.h"

@protocol DeGminoGeobaseSharedMapMarkerLayer < DeGminoGeobaseSharedMapMapLayer, NSObject >
- (void)addMarkerWithDeGminoGeobaseSharedMapMarker:(id<DeGminoGeobaseSharedMapMarker>)marker;
- (void)removeMarkerWithDeGminoGeobaseSharedMapMarker:(id<DeGminoGeobaseSharedMapMarker>)marker;
- (id<DeGminoGeobaseSharedMapMarker>)newMarkerWithDeGminoGeobaseSharedDomainLatLon:(DeGminoGeobaseSharedDomainLatLon *)location
                                                                      withNSString:(NSString *)title
                                                                      withNSString:(NSString *)description_
                                            withDeGminoGeobaseSharedDomainImageUrl:(DeGminoGeobaseSharedDomainImageUrl *)markerImage OBJC_METHOD_FAMILY_NONE;
@end