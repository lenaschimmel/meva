//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/cpy/de/gmino/meva/shared/request/RequestListener.java
//
//  Created by lena on 24.06.13.
//

@class JavaLangThrowable;
@protocol JavaUtilCollection;

#import "JreEmulation.h"

@interface DeGminoMevaSharedRequestRequestListener : NSObject {
 @public
  NSString *request_;
}

@property (nonatomic, copy) NSString *request;

- (void)onNewResultWithId:(id)result;
- (void)onFinishedWithJavaUtilCollection:(id<JavaUtilCollection>)results;
- (void)onErrorWithNSString:(NSString *)message
      withJavaLangThrowable:(JavaLangThrowable *)e;
- (id)init;
@end
