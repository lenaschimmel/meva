//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/cpy/de/gmino/geobase/shared/map/MarkerListener.java
//
//  Created by lena on 24.06.13.
//

@class DeGminoGeobaseSharedMapEventEnum;
@protocol DeGminoGeobaseSharedMapMarker;

#import "JreEmulation.h"

@protocol DeGminoGeobaseSharedMapMarkerListener < NSObject >
- (void)onEventWithDeGminoGeobaseSharedMapMarker:(id<DeGminoGeobaseSharedMapMarker>)marker
            withDeGminoGeobaseSharedMapEventEnum:(DeGminoGeobaseSharedMapEventEnum *)event;
@end