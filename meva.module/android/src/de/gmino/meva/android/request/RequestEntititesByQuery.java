package de.gmino.meva.android.request;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import de.gmino.meva.android.ValueAndroid;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.ReturnEntityPolicy;

/**
 * Executes a query asynchronously by making a HTTP request.
 * 
 * The results always come as a Collection of Result objects. This collection
 * may be empty, but will never be null. When the result is empty, onEmptyResult
 * will be called just before the call to onFinish.
 * 
 * @author lena
 * 
 * @param <Params>
 * @param <Result>
 */
public class RequestEntititesByQuery<Result extends Entity> {
	Context ctx;
	InjectibleAsyncTask<Result> task;

	public RequestEntititesByQuery(Context ctx, Query query,
			Class<Result> returnClass) {
		super();
		this.ctx = ctx;
		this.query = query;
		this.typeName = returnClass.getSimpleName();
		results = new LinkedList<Result>();
	}

	protected Query query;
	Collection<Result> results;
	String typeName;

	public void start() {
		task = new InjectibleAsyncTask<Result>() {
			protected Collection<Result> doInBackground(Void[] nothing) {
				run();
				if (results.isEmpty())
					onEmptyResult();
				onFinish(results);
				return results;
			};

			@Override
			protected void onPostExecute(Collection<Result> result) {
				if (results.isEmpty())
					onEmptyResultOnUi();
				onFinishOnUi(results);
			}

			@Override
			protected void onCancelled() {
				onError("Request was cancelled");
				onErrorOnUi("Request was cancelled");
			}

			protected void onProgressUpdate(Result[] values) {
				for (Result value : values)
					onNewResultOnUi(value);
			}
		};
		task.execute();
	}

	/**
	 * Implement this method do perform the actual request. During execution,
	 * you must call addResult for each result that you generate. Then simply
	 * return.
	 * 
	 * This method will be called on a thread / context where it is safe to
	 * perform network operations and/or long running calculations.
	 */
	@SuppressWarnings("unchecked")
	private void run() {

		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost();
			request.setURI(new URI("http://192.168.178.64:8888/"
					+ query.getUrlPostfix()));
			// StringBuilder sb = new StringBuilder();
			// query.serializeJson(sb);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			((ValueAndroid)query).serializeBinary(dos);

			HttpEntity postBody = new ByteArrayEntity(baos.toByteArray());
			request.setEntity(postBody);

			HttpResponse response = client.execute(request);
			DataInputStream dis = new DataInputStream(response.getEntity()
					.getContent());

			Collection<Long> ids = new LinkedList<Long>();

			long id = dis.readLong();
			while (id != 0) {
				ids.add(id);
				id = dis.readLong();
			}

			if (!ids.isEmpty()) {
				results.addAll((Collection<? extends Result>) EntityFactory.getEntitiesById(typeName, ids, ReturnEntityPolicy.BLOCK_IF_NEEDED));
			}

		} catch (Exception e) {
			e.printStackTrace();
			onError("Error while performing HTTP Post request: " + e.toString());
			task.cancel(true);
		}

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

	protected void onEmptyResult() {
		// To be overwritten.
	}

	protected void onEmptyResultOnUi() {
		// To be overwritten.
	}

	protected void onNewResult(Result result) {
		// To be overwritten.
	}

	protected void onNewResultOnUi(Result result) {
		// To be overwritten.
	}

	// internal, called by the specific request class
	private final void addResult(Result result) {
		results.add(result);
		task.newResult(result);
		onNewResult(result);
	}
}
