package de.gmino.meva.ios.request;

import java.util.HashMap;

/*-[
#import "RequestDelegate.h"
]-*/

public class RequestBuilder {

	public static final String POST = "POST";
	private String method;
	private String url;
	private HashMap<String, String> headers = new HashMap<String, String>();

	public RequestBuilder(String method, String url) {
		this.method = method;
		this.url = url;
	}

	public void setHeader(String key, String value) {
		headers.put(key, value);
	}
	
	public void sendRequest(String body, RequestCallback callback) {
		nSendRequest(body, callback, url, method, headers);
	}
	  

	public native void nSendRequest(String body, RequestCallback callback, String url, String method, HashMap<String, String> headers) /*-[
		NSMutableData *receivedData;
	
		// Create the request.
	    NSMutableURLRequest *request=[NSMutableURLRequest requestWithURL:[NSURL URLWithString:url]
	                                              cachePolicy:NSURLRequestUseProtocolCachePolicy
	                                          timeoutInterval:60.0];
	    
	    [request setHTTPMethod:method];
	    
	    id<JavaUtilIterator> iter__ = [((id<JavaUtilSet>) nil_chk([((JavaUtilHashMap *) nil_chk(headers)) entrySet])) iterator];
	    while ([((id<JavaUtilIterator>) nil_chk(iter__)) hasNext]) {
	      id<JavaUtilMap_Entry> entry = [iter__ next];
	      NSString *key = [((id<JavaUtilMap_Entry>) nil_chk(entry)) getKey];
	      NSString *value = [entry getValue];
	      [request setValue:value 
	    	forHTTPHeaderField:key];
	    }
	    
	    [request setValue:@"text/xml" 
	    	forHTTPHeaderField:@"Content-type"];
	    
	    [request setValue:[NSString stringWithFormat:@"%d",
	                       [body length]]
	    	forHTTPHeaderField:@"Content-length"];
	    
	    [request setHTTPBody:[body
	    	dataUsingEncoding:NSUTF8StringEncoding]];
	    
	    // create the connection with the request and start loading the data
	    NSURLConnection *theConnection=[[NSURLConnection alloc] initWithRequest:request delegate:[[RequestDelegate alloc] initWithMERequestCallback:callback]];
	    if (theConnection) {
	        // Create the NSMutableData to hold the received data. ReceivedData is an instance variable declared elsewhere.
	        receivedData = [NSMutableData data];
	    } else {
	        // Inform the user that the connection failed.
	    }
  	]-*/;

}
