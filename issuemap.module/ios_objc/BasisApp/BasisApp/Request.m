//
//  Request.m
//  BasisApp
//
//  Created by Tillmann Heigel on 03.09.13.
//  Copyright (c) 2013 Greenmobile Innovations. All rights reserved.
//

#import "Request.h"

@implementation Request
{
    NSMutableData *receivedData;
}

-(void)sendRequest{
    
    // Create the request.
    NSMutableURLRequest *request=[NSMutableURLRequest requestWithURL:[NSURL URLWithString:@"http://www.apple.com/"]
                                              cachePolicy:NSURLRequestUseProtocolCachePolicy
                                          timeoutInterval:60.0];
    
    [request setHTTPMethod:@"POST"];
    [request setValue:@"text/xml"
   forHTTPHeaderField:@"Content-type"];
    
    NSString *xmlString = @"<data><item>Item 1</item><item>Item 2</item></data>";
    
    [request setValue:[NSString stringWithFormat:@"%d",
                       [xmlString length]]
   forHTTPHeaderField:@"Content-length"];
    
    [request setHTTPBody:[xmlString
                          dataUsingEncoding:NSUTF8StringEncoding]];
    
    // create the connection with the request
    // and start loading the data
    NSURLConnection *theConnection=[[NSURLConnection alloc] initWithRequest:request delegate:self];
    if (theConnection) {
        // Create the NSMutableData to hold the received data.
        // receivedData is an instance variable declared elsewhere.
        receivedData = [NSMutableData data];
    } else {
        // Inform the user that the connection failed.
    }}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response
{
    NSLog(@"Jo, data recieved: %@", response);
    [receivedData setLength:0];
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
