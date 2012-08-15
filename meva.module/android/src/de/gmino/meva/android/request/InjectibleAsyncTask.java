package de.gmino.meva.android.request;

import java.util.Collection;

import android.os.AsyncTask;

abstract class InjectibleAsyncTask<Result> extends
		AsyncTask<Void, Result, Collection<Result>> {
	// we need this class to inject new results from the outside
	public void newResult(Result result) {
		// this method is called on the work thread, and the following call
		// passes it to the UI thread
		publishProgress(result);
	}
}