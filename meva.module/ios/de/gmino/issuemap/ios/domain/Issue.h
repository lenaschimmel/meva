//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/src/de/gmino/issuemap/ios/domain/Issue.java
//
//  Created by lena on 24.06.13.
//

@class DeGminoGeobaseIosDomainImageUrl;
@class DeGminoGeobaseIosDomainLatLon;
@class DeGminoGeobaseIosDomainTimestamp;
@class DeGminoIssuemapIosDomainMap;
@class DeGminoIssuemapIosDomainMarkertype;

#import "JreEmulation.h"
#import "de/gmino/issuemap/ios/domain/gen/IssueGen.h"

@interface DeGminoIssuemapIosDomainIssue : DeGminoIssuemapIosDomainGenIssueGen {
}

- (id)initWithLongInt:(long long int)id_;
- (id)initWithLongInt:(long long int)id_
             withBOOL:(BOOL)ready
withDeGminoGeobaseIosDomainLatLon:(DeGminoGeobaseIosDomainLatLon *)location
         withNSString:(NSString *)title
         withNSString:(NSString *)description_
withDeGminoIssuemapIosDomainMarkertype:(DeGminoIssuemapIosDomainMarkertype *)markertype
withDeGminoIssuemapIosDomainMap:(DeGminoIssuemapIosDomainMap *)map_instance
withDeGminoGeobaseIosDomainTimestamp:(DeGminoGeobaseIosDomainTimestamp *)creationTimestamp
              withInt:(int)rating
              withInt:(int)number_of_rating
             withBOOL:(BOOL)resolved
             withBOOL:(BOOL)deleted
withDeGminoGeobaseIosDomainImageUrl:(DeGminoGeobaseIosDomainImageUrl *)primary_picture
withDeGminoGeobaseIosDomainTimestamp:(DeGminoGeobaseIosDomainTimestamp *)eventTimestamp
           withDouble:(double)price
         withNSString:(NSString *)organizer
         withNSString:(NSString *)email
         withNSString:(NSString *)phone;
@end
