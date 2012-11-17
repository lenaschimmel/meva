package de.gmino.meva.shared.request;

import java.util.Collection;

public abstract class RequestListener<ResultType> {
	String request = "unknown";

	public void onNewResult(ResultType result) {

	}

	public void onFinished(Collection<ResultType> results) {

	}

	public void onError(String message, Throwable e) {
		throw new RuntimeException("Error in request (" + request + "):" + message, e);
	}
}
