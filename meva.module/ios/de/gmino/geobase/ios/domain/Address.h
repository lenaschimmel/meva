//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/cpy/de/gmino/geobase/ios/domain/Address.java
//
//  Created by lena on 24.06.13.
//

@protocol OrgItemscriptCoreValuesJsonObject;

#import "JreEmulation.h"
#import "de/gmino/geobase/ios/domain/gen/AddressGen.h"

@interface DeGminoGeobaseIosDomainAddress : DeGminoGeobaseIosDomainGenAddressGen {
}

- (id)init;
- (id)initWithOrgItemscriptCoreValuesJsonObject:(id<OrgItemscriptCoreValuesJsonObject>)json;
- (id)initWithNSString:(NSString *)recipientName
          withNSString:(NSString *)street
          withNSString:(NSString *)houseNumber
          withNSString:(NSString *)zip
          withNSString:(NSString *)city
          withNSString:(NSString *)additionalAddressLine;
@end
