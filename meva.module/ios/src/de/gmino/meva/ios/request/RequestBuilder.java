package de.gmino.meva.ios.request;

import java.util.HashMap;

public class RequestBuilder {

	public static final String POST = "post";
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
		// TODO Auto-generated method stub
		
	}
}
