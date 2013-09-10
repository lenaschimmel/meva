//
//  Request.m
//  BasisApp
//
//  Created by Tillmann Heigel on 03.09.13.
//  Copyright (c) 2013 Greenmobile Innovations. All rights reserved.
//

#import "RequestDelegate.h"
#import "de/gmino/meva/ios/request/RequestCallback.h"
#import "de/gmino/meva/ios/request/Response.h"

@implementation RequestDelegate
{
    id<DeGminoMevaIosRequestRequestCallback> callback;
}

-(id)initWithMERequestCallback:(id<DeGminoMevaIosRequestRequestCallback>) cb
{
    if((self = [super init])) {
        callback = cb;
    }
    return self;
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
    NSString* str = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    
    NSLog(@"Jo, data recieved: %@",str);
    
    
    [callback onResponseReceivedWithDeGminoMevaIosRequestRequest:nil withDeGminoMevaIosRequestResponse:[[DeGminoMevaIosRequestResponse alloc] initWithNSString: str]];
}

- (void)connection:(NSURLConnection *)connection
  didFailWithError:(NSError *)error
{  
    // inform the user
    NSLog(@"Connection failed! Error - %@ %@",
          [error localizedDescription],
          [[error userInfo] objectForKey:NSURLErrorFailingURLStringErrorKey]);
}


@end
