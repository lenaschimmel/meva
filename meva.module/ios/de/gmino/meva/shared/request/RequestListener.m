//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/cpy/de/gmino/meva/shared/request/RequestListener.java
//
//  Created by lena on 24.06.13.
//

#import "de/gmino/meva/shared/request/RequestListener.h"
#import "java/lang/RuntimeException.h"
#import "java/lang/Throwable.h"
#import "java/util/Collection.h"

@implementation DeGminoMevaSharedRequestRequestListener

@synthesize request = request_;

- (void)onNewResultWithId:(id)result {
}

- (void)onFinishedWithJavaUtilCollection:(id<JavaUtilCollection>)results {
}

- (void)onErrorWithNSString:(NSString *)message
      withJavaLangThrowable:(JavaLangThrowable *)e {
  @throw [[JavaLangRuntimeException alloc] initWithNSString:[NSString stringWithFormat:@"Error in request (%@):%@", request_, message] withJavaLangThrowable:e];
}

- (id)init {
  if ((self = [super init])) {
    request_ = @"unknown";
  }
  return self;
}

- (void)copyAllPropertiesTo:(id)copy {
  [super copyAllPropertiesTo:copy];
  DeGminoMevaSharedRequestRequestListener *typedCopy = (DeGminoMevaSharedRequestRequestListener *) copy;
  typedCopy.request = request_;
}

@end
