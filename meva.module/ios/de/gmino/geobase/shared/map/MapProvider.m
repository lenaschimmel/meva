//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/cpy/de/gmino/geobase/shared/map/MapProvider.java
//
//  Created by lena on 24.06.13.
//

#import "de/gmino/geobase/shared/map/MapProvider.h"
#import "java/lang/IllegalArgumentException.h"


static DeGminoGeobaseSharedMapMapProviderEnum *DeGminoGeobaseSharedMapMapProviderEnum_MAPNIK;
static DeGminoGeobaseSharedMapMapProviderEnum *DeGminoGeobaseSharedMapMapProviderEnum_OPEN_CYCLE_MAP;
static DeGminoGeobaseSharedMapMapProviderEnum *DeGminoGeobaseSharedMapMapProviderEnum_MAPQUEST;
static DeGminoGeobaseSharedMapMapProviderEnum *DeGminoGeobaseSharedMapMapProviderEnum_MAPQUEST_AERIAL;
static DeGminoGeobaseSharedMapMapProviderEnum *DeGminoGeobaseSharedMapMapProviderEnum_TRANSPORT;
static DeGminoGeobaseSharedMapMapProviderEnum *DeGminoGeobaseSharedMapMapProviderEnum_LANDSCAPE;
static DeGminoGeobaseSharedMapMapProviderEnum *DeGminoGeobaseSharedMapMapProviderEnum_NO_LABELS;
static DeGminoGeobaseSharedMapMapProviderEnum *DeGminoGeobaseSharedMapMapProviderEnum_HIKE_BIKE;
static DeGminoGeobaseSharedMapMapProviderEnum *DeGminoGeobaseSharedMapMapProviderEnum_OPEN_STREET_BROWSER;
static DeGminoGeobaseSharedMapMapProviderEnum *DeGminoGeobaseSharedMapMapProviderEnum_OE_P_N_V;
static DeGminoGeobaseSharedMapMapProviderEnum *DeGminoGeobaseSharedMapMapProviderEnum_WANDER_REIT;
IOSObjectArray *DeGminoGeobaseSharedMapMapProviderEnum_values;

@implementation DeGminoGeobaseSharedMapMapProviderEnum

+ (DeGminoGeobaseSharedMapMapProviderEnum *)MAPNIK {
  return DeGminoGeobaseSharedMapMapProviderEnum_MAPNIK;
}
+ (DeGminoGeobaseSharedMapMapProviderEnum *)OPEN_CYCLE_MAP {
  return DeGminoGeobaseSharedMapMapProviderEnum_OPEN_CYCLE_MAP;
}
+ (DeGminoGeobaseSharedMapMapProviderEnum *)MAPQUEST {
  return DeGminoGeobaseSharedMapMapProviderEnum_MAPQUEST;
}
+ (DeGminoGeobaseSharedMapMapProviderEnum *)MAPQUEST_AERIAL {
  return DeGminoGeobaseSharedMapMapProviderEnum_MAPQUEST_AERIAL;
}
+ (DeGminoGeobaseSharedMapMapProviderEnum *)TRANSPORT {
  return DeGminoGeobaseSharedMapMapProviderEnum_TRANSPORT;
}
+ (DeGminoGeobaseSharedMapMapProviderEnum *)LANDSCAPE {
  return DeGminoGeobaseSharedMapMapProviderEnum_LANDSCAPE;
}
+ (DeGminoGeobaseSharedMapMapProviderEnum *)NO_LABELS {
  return DeGminoGeobaseSharedMapMapProviderEnum_NO_LABELS;
}
+ (DeGminoGeobaseSharedMapMapProviderEnum *)HIKE_BIKE {
  return DeGminoGeobaseSharedMapMapProviderEnum_HIKE_BIKE;
}
+ (DeGminoGeobaseSharedMapMapProviderEnum *)OPEN_STREET_BROWSER {
  return DeGminoGeobaseSharedMapMapProviderEnum_OPEN_STREET_BROWSER;
}
+ (DeGminoGeobaseSharedMapMapProviderEnum *)OE_P_N_V {
  return DeGminoGeobaseSharedMapMapProviderEnum_OE_P_N_V;
}
+ (DeGminoGeobaseSharedMapMapProviderEnum *)WANDER_REIT {
  return DeGminoGeobaseSharedMapMapProviderEnum_WANDER_REIT;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

@synthesize urlName = urlName_;
@synthesize readableName = readableName_;

- (NSString *)getUrlName {
  return urlName_;
}

- (NSString *)getReadableName {
  return readableName_;
}

- (id)initWithNSString:(NSString *)urlName
          withNSString:(NSString *)readableName
          withNSString:(NSString *)name
               withInt:(int)ordinal {
  if ((self = [super initWithNSString:name withInt:ordinal])) {
    self.urlName = urlName;
    self.readableName = readableName;
  }
  return self;
}

+ (void)initialize {
  if (self == [DeGminoGeobaseSharedMapMapProviderEnum class]) {
    DeGminoGeobaseSharedMapMapProviderEnum_MAPNIK = [[DeGminoGeobaseSharedMapMapProviderEnum alloc] initWithNSString:@"mapnik" withNSString:@"OSM (Mapnik)" withNSString:@"DeGminoGeobaseSharedMapMapProvider_MAPNIK" withInt:0];
    DeGminoGeobaseSharedMapMapProviderEnum_OPEN_CYCLE_MAP = [[DeGminoGeobaseSharedMapMapProviderEnum alloc] initWithNSString:@"opencyclemap" withNSString:@"Open Cycle Map" withNSString:@"DeGminoGeobaseSharedMapMapProvider_OPEN_CYCLE_MAP" withInt:1];
    DeGminoGeobaseSharedMapMapProviderEnum_MAPQUEST = [[DeGminoGeobaseSharedMapMapProviderEnum alloc] initWithNSString:@"mapquest" withNSString:@"Mapquest" withNSString:@"DeGminoGeobaseSharedMapMapProvider_MAPQUEST" withInt:2];
    DeGminoGeobaseSharedMapMapProviderEnum_MAPQUEST_AERIAL = [[DeGminoGeobaseSharedMapMapProviderEnum alloc] initWithNSString:@"mapquestarial" withNSString:@"Mapquest Aerial" withNSString:@"DeGminoGeobaseSharedMapMapProvider_MAPQUEST_AERIAL" withInt:3];
    DeGminoGeobaseSharedMapMapProviderEnum_TRANSPORT = [[DeGminoGeobaseSharedMapMapProviderEnum alloc] initWithNSString:@"transport" withNSString:@"Transport" withNSString:@"DeGminoGeobaseSharedMapMapProvider_TRANSPORT" withInt:4];
    DeGminoGeobaseSharedMapMapProviderEnum_LANDSCAPE = [[DeGminoGeobaseSharedMapMapProviderEnum alloc] initWithNSString:@"landscape" withNSString:@"Landschaft" withNSString:@"DeGminoGeobaseSharedMapMapProvider_LANDSCAPE" withInt:5];
    DeGminoGeobaseSharedMapMapProviderEnum_NO_LABELS = [[DeGminoGeobaseSharedMapMapProviderEnum alloc] initWithNSString:@"nolabels" withNSString:@"Ohne Text" withNSString:@"DeGminoGeobaseSharedMapMapProvider_NO_LABELS" withInt:6];
    DeGminoGeobaseSharedMapMapProviderEnum_HIKE_BIKE = [[DeGminoGeobaseSharedMapMapProviderEnum alloc] initWithNSString:@"hikebike" withNSString:@"Hike & Bike" withNSString:@"DeGminoGeobaseSharedMapMapProvider_HIKE_BIKE" withInt:7];
    DeGminoGeobaseSharedMapMapProviderEnum_OPEN_STREET_BROWSER = [[DeGminoGeobaseSharedMapMapProviderEnum alloc] initWithNSString:@"openstreetbrowser" withNSString:@"Open Street Browser" withNSString:@"DeGminoGeobaseSharedMapMapProvider_OPEN_STREET_BROWSER" withInt:8];
    DeGminoGeobaseSharedMapMapProviderEnum_OE_P_N_V = [[DeGminoGeobaseSharedMapMapProviderEnum alloc] initWithNSString:@"oepnv" withNSString:@"ÖPNV" withNSString:@"DeGminoGeobaseSharedMapMapProvider_OE_P_N_V" withInt:9];
    DeGminoGeobaseSharedMapMapProviderEnum_WANDER_REIT = [[DeGminoGeobaseSharedMapMapProviderEnum alloc] initWithNSString:@"wanderreit" withNSString:@"Wander- & Reitkarte" withNSString:@"DeGminoGeobaseSharedMapMapProvider_WANDER_REIT" withInt:10];
    DeGminoGeobaseSharedMapMapProviderEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ DeGminoGeobaseSharedMapMapProviderEnum_MAPNIK, DeGminoGeobaseSharedMapMapProviderEnum_OPEN_CYCLE_MAP, DeGminoGeobaseSharedMapMapProviderEnum_MAPQUEST, DeGminoGeobaseSharedMapMapProviderEnum_MAPQUEST_AERIAL, DeGminoGeobaseSharedMapMapProviderEnum_TRANSPORT, DeGminoGeobaseSharedMapMapProviderEnum_LANDSCAPE, DeGminoGeobaseSharedMapMapProviderEnum_NO_LABELS, DeGminoGeobaseSharedMapMapProviderEnum_HIKE_BIKE, DeGminoGeobaseSharedMapMapProviderEnum_OPEN_STREET_BROWSER, DeGminoGeobaseSharedMapMapProviderEnum_OE_P_N_V, DeGminoGeobaseSharedMapMapProviderEnum_WANDER_REIT, nil } count:11 type:[IOSClass classWithClass:[DeGminoGeobaseSharedMapMapProviderEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:DeGminoGeobaseSharedMapMapProviderEnum_values];
}

+ (DeGminoGeobaseSharedMapMapProviderEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [DeGminoGeobaseSharedMapMapProviderEnum_values count]; i++) {
    DeGminoGeobaseSharedMapMapProviderEnum *e = [DeGminoGeobaseSharedMapMapProviderEnum_values objectAtIndex:i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

@end
