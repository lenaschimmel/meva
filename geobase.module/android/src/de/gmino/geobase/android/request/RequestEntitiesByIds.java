package de.gmino.geobase.android.request;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import de.gmino.geobase.android.EntityAndroid;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.ReturnEntityPolicy;

/**
 * TODO Why do we have this as an asynchronous request class? This is a
 * low-level request, only to be used from other high-level requests which have
 * their own async-handling code.
 * 
 * Performs a request to get several Entities of the same type by their ids.
 * After constructing the request object, you call start() from the UI thread.
 * 
 * Application programmers subclass this class anonymously to supply their
 * callback methods.
 * 
 * Basically, this class makes single request to the server, which will be
 * performed in a worker thread. Each callback exists in two variants: onX is
 * called on the worker thread, onXOnUi is called on the UI thread.
 * 
 * This class checks for entities that are already present locally. If all
 * requested entities are locally available, no request to the server will be
 * made at all and all callbacks are instantly called from the UI thread.
 * 
 * @author lena
 * 
 * @param <Result>
 */
public abstract class RequestEntitiesByIds<Result extends Entity> {
	Collection<Long> ids;
	String typeName;
	protected Collection<Result> results;
	InjectibleAsyncTask<Result> task;
	boolean callUiCallbacks;

	public RequestEntitiesByIds(String typeName, Collection<Long> ids) {
		this.ids = ids;
		this.typeName = typeName;
	}

	private void start() {
		results = new ArrayList<Result>(ids.size());

		if (ids.isEmpty()) {
			onEmptyResult();
			if (callUiCallbacks)
				onEmptyResultOnUi();
			onFinish(results);
			if (callUiCallbacks)
				onFinishOnUi(results);
		}

		final StringBuffer sbRequestUrl = new StringBuffer(
				"http://gmino.de:8080/YourProject/getentities?typename="
						+ typeName + "&ids=");
		int requestIdCount = 0;
		for (long id : ids) {
			Result entity = (Result) EntityFactory.getEntityById(typeName, id,
					ReturnEntityPolicy.RETURN_UNLOADED);
			if (entity.isReady()) {

				onNewResult(entity);
				onNewResultOnUi(entity);
				results.add(entity);
			} else {
				requestIdCount++;
				sbRequestUrl.append(id).append(',');
			}
		}

		if (requestIdCount == 0) // do we already have all entities that we
									// need?
		{
			onFinish(results);
			onFinishOnUi(results);
		} else {
			if (callUiCallbacks) {
				task = new InjectibleAsyncTask<Result>() {
					@Override
					protected Collection<Result> doInBackground(Void... params) {
						return performRequest(sbRequestUrl);
					}

					protected void onProgressUpdate(Result[] values) {
						for (Result value : values)
							onNewResultOnUi(value);
					}

					@Override
					protected void onPostExecute(Collection<Result> result) {
						onFinishOnUi(results);
					}
				};
				task.execute();
			} else
				performRequest(sbRequestUrl);
		}
	}

	public void startFromWorkerThread() {
		callUiCallbacks = false;
		start();
	}

	public void startFromUiThread() {
		callUiCallbacks = true;
		start();
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

	private Collection<Result> performRequest(final StringBuffer sbRequestUrl) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			sbRequestUrl.setLength(sbRequestUrl.length() - 1);
			request.setURI(new URI(sbRequestUrl.toString()));
			HttpResponse response = client.execute(request);
			InputStream is = response.getEntity().getContent();
			DataInputStream dis = new DataInputStream(is);
			long nextId = dis.readLong();
			while (nextId > 0) {
				Result result = (Result) EntityFactory.getEntityById(typeName,
						nextId, ReturnEntityPolicy.RETURN_UNLOADED);
				((EntityAndroid) result).deserializeBinary(dis);
				onNewResult(result);
				if (task != null)
					task.newResult(result);
				results.add(result);

				nextId = dis.readLong();
			}
			onFinish(results);
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
