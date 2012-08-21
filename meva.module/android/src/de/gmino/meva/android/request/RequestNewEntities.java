package de.gmino.meva.android.request;

import java.util.Collection;

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;

public class RequestNewEntities<Result extends Entity> {
	// TODO We need this as an asynchronous high-level request, but we don't need it right now.
	
	String typeName;
	int count;
	
	public RequestNewEntities(String typeName, int count) {
		super();
		this.typeName = typeName;
		this.count = count;
	}
	
	public void start()
	{
		
		new InjectibleAsyncTask<Result>() {
			@Override
			protected Collection<Result> doInBackground(Void... params) {
				Collection<Result> newEntities = (Collection<Result>) EntityFactory.getNewEntities(typeName, count);
				onFinish(newEntities);
				return newEntities;
			}
			
			@Override
			protected void onPostExecute(Collection<Result> result) {
				super.onPostExecute(result);
				onFinishOnUi(result);
			}
			
			@Override
			protected void onCancelled() {
				super.onCancelled();
				onError("Request was cancelled");
				onErrorOnUi("Request was cancelled");
			}
		}.execute();
	}
	

	protected void onFinish(Collection<Result> results) {
		// To be overwritten.
	}

	protected void onFinishOnUi(Collection<Result> results) {
		// To be overwritten.
	}

	protected void onError(String message) {
		// To be overwritten.
	}

	protected void onErrorOnUi(String message) {
		// To be overwritten.
		System.err.println(message);
		throw new RuntimeException("Error in "
				+ this.getClass().getSimpleName() + ": " + message);
	}
}
