//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/src/de/gmino/issuemap/ios/domain/Photo.java
//
//  Created by lena on 24.06.13.
//

@class DeGminoGeobaseIosDomainImageUrl;
@class DeGminoGeobaseIosDomainTimestamp;
@class DeGminoIssuemapIosDomainIssue;

#import "JreEmulation.h"
#import "de/gmino/issuemap/ios/domain/gen/PhotoGen.h"

@interface DeGminoIssuemapIosDomainPhoto : DeGminoIssuemapIosDomainGenPhotoGen {
}

- (id)initWithLongInt:(long long int)id_;
- (id)initWithLongInt:(long long int)id_
             withBOOL:(BOOL)ready
withDeGminoIssuemapIosDomainIssue:(DeGminoIssuemapIosDomainIssue *)issue
withDeGminoGeobaseIosDomainImageUrl:(DeGminoGeobaseIosDomainImageUrl *)image
         withNSString:(NSString *)user
withDeGminoGeobaseIosDomainTimestamp:(DeGminoGeobaseIosDomainTimestamp *)timestamp
             withBOOL:(BOOL)deleted;
@end
