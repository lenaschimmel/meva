package de.gmino.meva.android.request;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.ReturnEntityPolicy;

/**
 * TODO Why do we have this as an asynchronous request class? This is a
 * low-level request, only to be used from other high-level requests which have
 * their own async-handling code.
 * 
 * FIXME This class lacks the AsyncTask and just does everything synchronously.
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
@Deprecated
public abstract class RequestEntitiesByIds<Result extends Entity> {
	Collection<Long> ids;
	String typeName;
	protected Collection<Result> results;
	InjectibleAsyncTask<Result> task;
	boolean callUiCallbacks;

	public RequestEntitiesByIds(String typeName, Collection<Long> ids) {
		this.ids = ids;
		this.typeName = typeName;
		throw new RuntimeException("This class should not be used at all, see documentation.");
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

		Collection<Entity> entitiedToLoad = new LinkedList<Entity>();
		int requestIdCount = 0;
		for (long id : ids) {
			Result entity = (Result) EntityFactory.getEntityById(typeName, id,
					ReturnEntityPolicy.RETURN_UNLOADED);
			results.add(entity);
			if (entity.isReady()) {

				onNewResult(entity);
				onNewResultOnUi(entity);
			} else
				entitiedToLoad.add(entity);
		}

		if (!entitiedToLoad.isEmpty())
			EntityFactory.loadEntities(entitiedToLoad);

		onFinish(results);
		onFinishOnUi(results);

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
}
