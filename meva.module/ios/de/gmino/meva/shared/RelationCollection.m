//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /home/lena/meva/issuemap.module/ios/cpy/de/gmino/meva/shared/RelationCollection.java
//
//  Created by lena on 24.06.13.
//

#import "IOSClass.h"
#import "de/gmino/meva/shared/Entity.h"
#import "de/gmino/meva/shared/RelationCollection.h"
#import "java/util/Iterator.h"
#import "java/util/TreeSet.h"

@implementation DeGminoMevaSharedRelationCollection

@synthesize container = container_;
@synthesize relname = relname_;
@synthesize items = items_;

- (id)initWithDeGminoMevaSharedEntity:(id<DeGminoMevaSharedEntity>)container
                         withNSString:(NSString *)relname {
  if ((self = [super init])) {
    self.container = container;
    self.relname = relname;
    self.items = [[JavaUtilTreeSet alloc] init];
  }
  return self;
}

- (id<JavaUtilIterator>)iterator {
  return ((id<JavaUtilIterator>) [((JavaUtilTreeSet *) NIL_CHK(items_)) iterator]);
}

- (int)size {
  return [((JavaUtilTreeSet *) NIL_CHK(items_)) size];
}

- (BOOL)containsWithId:(id)o {
  return [((JavaUtilTreeSet *) NIL_CHK(items_)) containsWithId:o];
}

- (BOOL)addWithId:(id)e {
  [((id<DeGminoMevaSharedEntity>) e) reassignRelationWithNSString:relname_ withDeGminoMevaSharedEntity:container_];
  return [((JavaUtilTreeSet *) NIL_CHK(items_)) addWithId:(id<DeGminoMevaSharedEntity>) e];
}

- (BOOL)removeWithId:(id)o {
  if ([o conformsToProtocol: @protocol(DeGminoMevaSharedEntity)]) {
    id<DeGminoMevaSharedEntity> e = (id<DeGminoMevaSharedEntity>) o;
    [((id<DeGminoMevaSharedEntity>) NIL_CHK(e)) reassignRelationWithNSString:relname_ withDeGminoMevaSharedEntity:nil];
  }
  return (BOOL) [super removeWithId:o];
}

- (void)copyAllPropertiesTo:(id)copy {
  [super copyAllPropertiesTo:copy];
  DeGminoMevaSharedRelationCollection *typedCopy = (DeGminoMevaSharedRelationCollection *) copy;
  typedCopy.container = container_;
  typedCopy.relname = relname_;
  typedCopy.items = items_;
}

@end
@implementation DeGminoMevaSharedRelationCollection_RemoveSensitiveIterator

@synthesize this$0 = this$0_;
@synthesize base = base_;
@synthesize lastReturned = lastReturned_;

- (id)initWithDeGminoMevaSharedRelationCollection:(DeGminoMevaSharedRelationCollection *)outer$
                             withJavaUtilIterator:(id<JavaUtilIterator>)base {
  if ((self = [super init])) {
    this$0_ = outer$;
    self.base = base;
  }
  return self;
}

- (BOOL)hasNext {
  return [((id<JavaUtilIterator>) NIL_CHK(base_)) hasNext];
}

- (id)next {
  lastReturned_ = ((id<DeGminoMevaSharedEntity>) [((id<JavaUtilIterator>) NIL_CHK(base_)) next]);
  return lastReturned_;
}

- (void)remove {
  [((id<JavaUtilIterator>) NIL_CHK(base_)) remove];
  [((id<DeGminoMevaSharedEntity>) NIL_CHK(lastReturned_)) reassignRelationWithNSString:this$0_.relname withDeGminoMevaSharedEntity:nil];
}

- (void)copyAllPropertiesTo:(id)copy {
  [super copyAllPropertiesTo:copy];
  DeGminoMevaSharedRelationCollection_RemoveSensitiveIterator *typedCopy = (DeGminoMevaSharedRelationCollection_RemoveSensitiveIterator *) copy;
  typedCopy.this$0 = this$0_;
  typedCopy.base = base_;
  typedCopy.lastReturned = lastReturned_;
}

@end
