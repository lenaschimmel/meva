//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/cpy/de/gmino/issuemap/shared/domain/Comment.java
//
//  Created by lena on 24.06.13.
//

@class DeGminoGeobaseSharedDomainTimestamp;
@class DeGminoIssuemapSharedDomainIssue;

#import "JreEmulation.h"
#import "de/gmino/issuemap/shared/domain/gen/CommentGen.h"

@interface DeGminoIssuemapSharedDomainComment : DeGminoIssuemapSharedDomainGenCommentGen {
}

- (id)initWithLongInt:(long long int)id_;
- (id)initWithLongInt:(long long int)id_
             withBOOL:(BOOL)ready
withDeGminoIssuemapSharedDomainIssue:(DeGminoIssuemapSharedDomainIssue *)issue
         withNSString:(NSString *)text
         withNSString:(NSString *)user
withDeGminoGeobaseSharedDomainTimestamp:(DeGminoGeobaseSharedDomainTimestamp *)timestamp
             withBOOL:(BOOL)deleted;
@end