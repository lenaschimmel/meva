//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/cpy/de/gmino/geobase/ios/request/QueryGeoObject.java
//
//  Created by lena on 24.06.13.
//

@class DeGminoGeobaseIosDomainLatLonRect;
@protocol OrgItemscriptCoreValuesJsonObject;

#import "JreEmulation.h"
#import "de/gmino/geobase/ios/request/gen/QueryGeoObjectGen.h"

@interface DeGminoGeobaseIosRequestQueryGeoObject : DeGminoGeobaseIosRequestGenQueryGeoObjectGen {
}

- (id)init;
- (id)initWithOrgItemscriptCoreValuesJsonObject:(id<OrgItemscriptCoreValuesJsonObject>)json;
- (id)initWithDeGminoGeobaseIosDomainLatLonRect:(DeGminoGeobaseIosDomainLatLonRect *)area
                                        withInt:(int)maxCount;
@end