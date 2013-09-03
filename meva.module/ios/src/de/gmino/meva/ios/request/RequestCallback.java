package de.gmino.meva.ios.request;

public interface RequestCallback {

	void onResponseReceived(Request request, Response response);

	void onError(Request request, Throwable exception);

}
