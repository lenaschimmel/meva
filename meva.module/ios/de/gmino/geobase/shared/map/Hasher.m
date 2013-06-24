//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/cpy/de/gmino/geobase/shared/map/Hasher.java
//
//  Created by lena on 24.06.13.
//

#import "de/gmino/geobase/shared/map/Hasher.h"

@implementation DeGminoGeobaseSharedMapHasher

@synthesize hash_ = hash__;

- (void)hashIntWithInt:(int)i {
  hash__ = ((hash__ + i) * 67867967) % 961748927;
}

- (void)hashLongWithLongInt:(long long int)l {
  [self hashIntWithInt:(int) l];
  [self hashIntWithInt:(int) (l >> 32)];
}

- (void)hashCharWithUnichar:(unichar)c {
  [self hashIntWithInt:c];
}

- (void)hashShortWithShortInt:(short int)s {
  [self hashIntWithInt:s];
}

- (void)hashObjectWithId:(id)o {
  [self hashIntWithInt:[NIL_CHK(o) hash]];
}

- (void)hashFloatWithFloat:(float)f {
  [self hashIntWithInt:(int) f];
  if (f != 0) [self hashIntWithInt:(int) (1 / f)];
  if (f > 0 && f != 1) [self hashIntWithInt:(int) (1 / (1 - f))];
  if (f < 0 && f != -1) [self hashIntWithInt:(int) (1 / (-1 - f))];
}

- (void)hashDoubleWithDouble:(double)d {
  [self hashIntWithInt:(int) d];
  if (d != 0) [self hashIntWithInt:(int) (1 / d)];
  if (d > 0 && d != 1) [self hashIntWithInt:(int) (1 / (1 - d))];
  if (d < 0 && d != -1) [self hashIntWithInt:(int) (1 / (-1 - d))];
}

- (void)hashBooleanWithBOOL:(BOOL)b {
  [self hashIntWithInt:b ? 6709 : 7823];
}

- (int)getValue {
  return hash__;
}

- (void)reset {
  hash__ = 214523;
}

- (id)init {
  if ((self = [super init])) {
    hash__ = 214523;
  }
  return self;
}

- (void)copyAllPropertiesTo:(id)copy {
  [super copyAllPropertiesTo:copy];
  DeGminoGeobaseSharedMapHasher *typedCopy = (DeGminoGeobaseSharedMapHasher *) copy;
  typedCopy.hash_ = hash__;
}

@end
