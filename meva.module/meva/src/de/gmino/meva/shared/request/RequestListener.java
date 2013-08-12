package de.gmino.meva.shared.request;

import java.util.Collection;

public abstract class RequestListener<ResultType> {
	String request = "unknown";
	Throwable pointOfConstruction;
	
	public RequestListener() {
		pointOfConstruction = new Throwable();
	}

	public void onNewResult(ResultType result) {

	}

	public void onFinished(Collection<ResultType> results) {

	}

	public void onError(String message, Throwable e) {
		System.err.println("A request caused an exception. The Request listener was constructed here:");
		pointOfConstruction.printStackTrace();
		throw new RuntimeException("Error in request (" + request + "):" + message, e);
	}
}
