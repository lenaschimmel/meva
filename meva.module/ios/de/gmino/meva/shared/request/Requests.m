//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/cpy/de/gmino/meva/shared/request/Requests.java
//
//  Created by lena on 24.06.13.
//

#import "de/gmino/meva/shared/Entity.h"
#import "de/gmino/meva/shared/EntityFactory.h"
#import "de/gmino/meva/shared/EntityQuery.h"
#import "de/gmino/meva/shared/EntityTypeName.h"
#import "de/gmino/meva/shared/ValueQuery.h"
#import "de/gmino/meva/shared/request/NetworkRequests.h"
#import "de/gmino/meva/shared/request/RequestListener.h"
#import "de/gmino/meva/shared/request/Requests.h"
#import "java/io/PrintStream.h"
#import "java/lang/Long.h"
#import "java/lang/RuntimeException.h"
#import "java/lang/System.h"
#import "java/lang/Throwable.h"
#import "java/util/Collection.h"
#import "java/util/Iterator.h"
#import "java/util/LinkedList.h"

@implementation DeGminoMevaSharedRequestRequests

static id<DeGminoMevaSharedRequestNetworkRequests> DeGminoMevaSharedRequestRequests_networkImpl_;

+ (id<DeGminoMevaSharedRequestNetworkRequests>)networkImpl {
  return DeGminoMevaSharedRequestRequests_networkImpl_;
}

+ (void)setNetworkImpl:(id<DeGminoMevaSharedRequestNetworkRequests>)networkImpl {
  DeGminoMevaSharedRequestRequests_networkImpl_ = networkImpl;
}

+ (void)setImplementationWithDeGminoMevaSharedRequestNetworkRequests:(id<DeGminoMevaSharedRequestNetworkRequests>)networkImpl {
  DeGminoMevaSharedRequestRequests_networkImpl_ = networkImpl;
}

+ (void)ensureImplementation {
  if (DeGminoMevaSharedRequestRequests_networkImpl_ == nil) @throw [[JavaLangRuntimeException alloc] initWithNSString:@" Requests.setImplementation must be called before using any of its other methods."];
}

+ (void)getIdsByQueryWithDeGminoMevaSharedEntityQuery:(id<DeGminoMevaSharedEntityQuery>)q
          withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)listener {
  [DeGminoMevaSharedRequestRequests ensureImplementation];
  [((id<DeGminoMevaSharedRequestNetworkRequests>) NIL_CHK(DeGminoMevaSharedRequestRequests_networkImpl_)) getIdsByQueryWithDeGminoMevaSharedEntityQuery:q withDeGminoMevaSharedRequestRequestListener:listener];
}

+ (void)getValuesByQueryWithDeGminoMevaSharedValueQuery:(id<DeGminoMevaSharedValueQuery>)query
            withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)listener {
  [DeGminoMevaSharedRequestRequests ensureImplementation];
  [((id<DeGminoMevaSharedRequestNetworkRequests>) NIL_CHK(DeGminoMevaSharedRequestRequests_networkImpl_)) getValuesByQueryWithDeGminoMevaSharedValueQuery:query withDeGminoMevaSharedRequestRequestListener:listener];
}

+ (void)getLoadedEntityByIdWithDeGminoMevaSharedEntityTypeName:(DeGminoMevaSharedEntityTypeName *)type
                                                   withLongInt:(long long int)id_
                   withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)listener {
  id<JavaUtilCollection> list = [[JavaUtilLinkedList alloc] init];
  [((id<JavaUtilCollection>) NIL_CHK(list)) addWithId:[JavaLangLong valueOfWithLongInt:id_]];
  [DeGminoMevaSharedRequestRequests getLoadedEntitiesByIdWithDeGminoMevaSharedEntityTypeName:type withJavaUtilCollection:list withDeGminoMevaSharedRequestRequestListener:listener];
}

+ (void)getLoadedEntitiesByIdWithDeGminoMevaSharedEntityTypeName:(DeGminoMevaSharedEntityTypeName *)type
                                          withJavaUtilCollection:(id<JavaUtilCollection>)ids
                     withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)listener {
  [DeGminoMevaSharedRequestRequests ensureImplementation];
  id<JavaUtilCollection> entities = ((id<JavaUtilCollection>) [DeGminoMevaSharedEntityFactory getUnloadedEntitiesByIdWithDeGminoMevaSharedEntityTypeName:type withJavaUtilCollection:ids]);
  [DeGminoMevaSharedRequestRequests loadEntitiesWithJavaUtilCollection:entities withDeGminoMevaSharedRequestRequestListener:listener];
}

+ (void)getLoadedEntitiesByQueryWithDeGminoMevaSharedEntityTypeName:(DeGminoMevaSharedEntityTypeName *)type
                                   withDeGminoMevaSharedEntityQuery:(id<DeGminoMevaSharedEntityQuery>)q
                        withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)listener {
  [DeGminoMevaSharedRequestRequests ensureImplementation];
  [DeGminoMevaSharedRequestRequests getIdsByQueryWithDeGminoMevaSharedEntityQuery:q withDeGminoMevaSharedRequestRequestListener:[[DeGminoMevaSharedRequestRequests_$1 alloc] initWithDeGminoMevaSharedEntityTypeName:type withDeGminoMevaSharedRequestRequestListener:listener]];
}

+ (void)getNewEntityWithDeGminoMevaSharedEntityTypeName:(DeGminoMevaSharedEntityTypeName *)type
            withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)listener {
  [DeGminoMevaSharedRequestRequests getNewEntitiesWithDeGminoMevaSharedEntityTypeName:type withInt:1 withDeGminoMevaSharedRequestRequestListener:listener];
}

+ (void)getNewEntitiesWithDeGminoMevaSharedEntityTypeName:(DeGminoMevaSharedEntityTypeName *)type
                                                  withInt:(int)count
              withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)listener {
  [DeGminoMevaSharedRequestRequests ensureImplementation];
  [DeGminoMevaSharedRequestRequests getNewIdsWithDeGminoMevaSharedEntityTypeName:type withInt:count withDeGminoMevaSharedRequestRequestListener:[[DeGminoMevaSharedRequestRequests_$2 alloc] initWithDeGminoMevaSharedEntityTypeName:type withDeGminoMevaSharedRequestRequestListener:listener]];
}

+ (void)getNewIdsWithDeGminoMevaSharedEntityTypeName:(DeGminoMevaSharedEntityTypeName *)type
                                             withInt:(int)count
         withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)listener {
  [DeGminoMevaSharedRequestRequests ensureImplementation];
  [((id<DeGminoMevaSharedRequestNetworkRequests>) NIL_CHK(DeGminoMevaSharedRequestRequests_networkImpl_)) getNewIdsWithDeGminoMevaSharedEntityTypeName:type withInt:count withDeGminoMevaSharedRequestRequestListener:listener];
}

+ (void)getUnloadedEntitiesByQueryWithDeGminoMevaSharedEntityTypeName:(DeGminoMevaSharedEntityTypeName *)type
                                     withDeGminoMevaSharedEntityQuery:(id<DeGminoMevaSharedEntityQuery>)q
                          withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)listener {
  [DeGminoMevaSharedRequestRequests ensureImplementation];
  [DeGminoMevaSharedRequestRequests getIdsByQueryWithDeGminoMevaSharedEntityQuery:q withDeGminoMevaSharedRequestRequestListener:[[DeGminoMevaSharedRequestRequests_$3 alloc] initWithDeGminoMevaSharedEntityTypeName:type withDeGminoMevaSharedRequestRequestListener:listener]];
}

+ (void)loadEntityWithId:(id<DeGminoMevaSharedEntity>)entity
withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)listener {
  id<JavaUtilCollection> entities = [[JavaUtilLinkedList alloc] init];
  [((id<JavaUtilCollection>) NIL_CHK(entities)) addWithId:entity];
  [DeGminoMevaSharedRequestRequests loadEntitiesWithJavaUtilCollection:entities withDeGminoMevaSharedRequestRequestListener:listener];
}

+ (void)saveEntityWithId:(id<DeGminoMevaSharedEntity>)entity
withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)listener {
  id<JavaUtilCollection> entities = [[JavaUtilLinkedList alloc] init];
  [((id<JavaUtilCollection>) NIL_CHK(entities)) addWithId:entity];
  [DeGminoMevaSharedRequestRequests saveEntitiesWithJavaUtilCollection:entities withDeGminoMevaSharedRequestRequestListener:listener];
}

+ (void)loadEntitiesWithJavaUtilCollection:(id<JavaUtilCollection>)entities
withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)listener {
  [DeGminoMevaSharedRequestRequests ensureImplementation];
  [DeGminoMevaSharedRequestRequests ensureSameTypesWithJavaUtilCollection:entities];
  [((id<DeGminoMevaSharedRequestNetworkRequests>) NIL_CHK(DeGminoMevaSharedRequestRequests_networkImpl_)) loadEntitiesWithJavaUtilCollection:entities withDeGminoMevaSharedRequestRequestListener:listener];
}

+ (void)saveEntitiesWithJavaUtilCollection:(id<JavaUtilCollection>)entities
withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)listener {
  [DeGminoMevaSharedRequestRequests ensureImplementation];
  [DeGminoMevaSharedRequestRequests ensureSameTypesWithJavaUtilCollection:entities];
  [((id<DeGminoMevaSharedRequestNetworkRequests>) NIL_CHK(DeGminoMevaSharedRequestRequests_networkImpl_)) saveEntitiesWithJavaUtilCollection:entities withDeGminoMevaSharedRequestRequestListener:listener];
}

+ (void)ensureSameTypesWithJavaUtilCollection:(id<JavaUtilCollection>)entities {
  if ([((id<JavaUtilCollection>) NIL_CHK(entities)) isEmpty]) {
    [((JavaIoPrintStream *) NIL_CHK([JavaLangSystem err])) printlnWithNSString:@"Warning: ensureSameTypes on an empty collection."];
    return;
  }
  DeGminoMevaSharedEntityTypeName *fistTypeName = [((id<DeGminoMevaSharedEntity>) [((id<JavaUtilIterator>) [((id<JavaUtilCollection>) NIL_CHK(entities)) iterator]) next]) getType];
  {
    id<JavaUtilIterator> iter__ = ((id<JavaUtilIterator>) [((id<JavaUtilCollection>) NIL_CHK(entities)) iterator]);
    while ([((id<JavaUtilIterator>) NIL_CHK(iter__)) hasNext]) {
      id<DeGminoMevaSharedEntity> e = ((id<DeGminoMevaSharedEntity>) [((id<JavaUtilIterator>) NIL_CHK(iter__)) next]);
      if (fistTypeName != [((id<DeGminoMevaSharedEntity>) NIL_CHK(e)) getType]) @throw [[JavaLangRuntimeException alloc] initWithNSString:[NSString stringWithFormat:@"Heterogenous types in Request: first Entity has type %@, another one has %@", fistTypeName, [((id<DeGminoMevaSharedEntity>) NIL_CHK(e)) getType]]];
    }
  }
}

- (id)init {
  return [super init];
}

@end
@implementation DeGminoMevaSharedRequestRequests_$1

@synthesize val$type = val$type_;
@synthesize val$listener = val$listener_;

- (void)onFinishedWithJavaUtilCollection:(id<JavaUtilCollection>)ids {
  id<JavaUtilCollection> entities = ((id<JavaUtilCollection>) [DeGminoMevaSharedEntityFactory getUnloadedEntitiesByIdWithDeGminoMevaSharedEntityTypeName:val$type_ withJavaUtilCollection:ids]);
  [DeGminoMevaSharedRequestRequests loadEntitiesWithJavaUtilCollection:entities withDeGminoMevaSharedRequestRequestListener:val$listener_];
}

- (void)onErrorWithNSString:(NSString *)message
      withJavaLangThrowable:(JavaLangThrowable *)e {
  [((DeGminoMevaSharedRequestRequestListener *) NIL_CHK(val$listener_)) onErrorWithNSString:message withJavaLangThrowable:e];
}

- (id)initWithDeGminoMevaSharedEntityTypeName:(DeGminoMevaSharedEntityTypeName *)capture$0
  withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)capture$1 {
  if ((self = [super init])) {
    val$type_ = capture$0;
    val$listener_ = capture$1;
  }
  return self;
}

- (void)copyAllPropertiesTo:(id)copy {
  [super copyAllPropertiesTo:copy];
  DeGminoMevaSharedRequestRequests_$1 *typedCopy = (DeGminoMevaSharedRequestRequests_$1 *) copy;
  typedCopy.val$type = val$type_;
  typedCopy.val$listener = val$listener_;
}

@end
@implementation DeGminoMevaSharedRequestRequests_$2

@synthesize val$type = val$type_;
@synthesize val$listener = val$listener_;

- (void)onFinishedWithJavaUtilCollection:(id<JavaUtilCollection>)ids {
  id<JavaUtilCollection> entitites = ((id<JavaUtilCollection>) [DeGminoMevaSharedEntityFactory getUnloadedEntitiesByIdWithDeGminoMevaSharedEntityTypeName:val$type_ withJavaUtilCollection:ids]);
  [((DeGminoMevaSharedRequestRequestListener *) NIL_CHK(val$listener_)) onFinishedWithJavaUtilCollection:entitites];
}

- (void)onNewResultWithId:(JavaLangLong *)result {
  id<DeGminoMevaSharedEntity> e = (id<DeGminoMevaSharedEntity>) [DeGminoMevaSharedEntityFactory getUnloadedEntityByIdWithDeGminoMevaSharedEntityTypeName:val$type_ withLongInt:[((JavaLangLong *) NIL_CHK(result)) longLongValue]];
  [((DeGminoMevaSharedRequestRequestListener *) NIL_CHK(val$listener_)) onNewResultWithId:e];
}

- (void)onErrorWithNSString:(NSString *)message
      withJavaLangThrowable:(JavaLangThrowable *)e {
  [((DeGminoMevaSharedRequestRequestListener *) NIL_CHK(val$listener_)) onErrorWithNSString:message withJavaLangThrowable:e];
}

- (id)initWithDeGminoMevaSharedEntityTypeName:(DeGminoMevaSharedEntityTypeName *)capture$0
  withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)capture$1 {
  if ((self = [super init])) {
    val$type_ = capture$0;
    val$listener_ = capture$1;
  }
  return self;
}

- (void)copyAllPropertiesTo:(id)copy {
  [super copyAllPropertiesTo:copy];
  DeGminoMevaSharedRequestRequests_$2 *typedCopy = (DeGminoMevaSharedRequestRequests_$2 *) copy;
  typedCopy.val$type = val$type_;
  typedCopy.val$listener = val$listener_;
}

@end
@implementation DeGminoMevaSharedRequestRequests_$3

@synthesize val$type = val$type_;
@synthesize val$listener = val$listener_;

- (void)onFinishedWithJavaUtilCollection:(id<JavaUtilCollection>)ids {
  id<JavaUtilCollection> entities = ((id<JavaUtilCollection>) [DeGminoMevaSharedEntityFactory getUnloadedEntitiesByIdWithDeGminoMevaSharedEntityTypeName:val$type_ withJavaUtilCollection:ids]);
  [((DeGminoMevaSharedRequestRequestListener *) NIL_CHK(val$listener_)) onFinishedWithJavaUtilCollection:entities];
}

- (id)initWithDeGminoMevaSharedEntityTypeName:(DeGminoMevaSharedEntityTypeName *)capture$0
  withDeGminoMevaSharedRequestRequestListener:(DeGminoMevaSharedRequestRequestListener *)capture$1 {
  if ((self = [super init])) {
    val$type_ = capture$0;
    val$listener_ = capture$1;
  }
  return self;
}

- (void)copyAllPropertiesTo:(id)copy {
  [super copyAllPropertiesTo:copy];
  DeGminoMevaSharedRequestRequests_$3 *typedCopy = (DeGminoMevaSharedRequestRequests_$3 *) copy;
  typedCopy.val$type = val$type_;
  typedCopy.val$listener = val$listener_;
}

@end
