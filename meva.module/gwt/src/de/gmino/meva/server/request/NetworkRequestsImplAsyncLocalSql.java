package de.gmino.meva.server.request;

import java.util.Collection;

import com.google.gwt.http.client.RequestException;

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.TypeName;
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.ValueQuery;
import de.gmino.meva.shared.request.NetworkRequests;
import de.gmino.meva.shared.request.RequestListener;

/**
 * On the server, there aren't any real asynchronous requests. Therefore, this
 * class just delegates to its synchronous companion, LocalRequetsImpl and acts
 * as if this was done asynchronously.
 * 
 * @author lena
 * 
 */
public class NetworkRequestsImplAsyncLocalSql implements NetworkRequests {

	@Override
	public void getIdsByQuery(EntityQuery query, RequestListener<Long> listener) {
		Collection<Long> results = query.evaluate();
		for(Long result : results)
			listener.onNewResult(result);
		listener.onFinished(results);
	}

	@Override
	public <ValueClass extends Value> void getValuesByQuery(ValueQuery query, RequestListener<ValueClass> listener) {
		Collection<ValueClass> results = query.evaluate();
		for(ValueClass result : results)
			listener.onNewResult(result);
		listener.onFinished(results);
	}

	@Override
	public void getNewIds(TypeName type, int count, RequestListener<Long> listener) {
		Collection<Long> results = LocalRequetsImpl.getNewIds(type, count);
		for(Long result : results)
			listener.onNewResult(result);
		listener.onFinished(results);
	}

	@Override
	public <EntityClass extends Entity> void loadEntities(Collection<EntityClass> entities, RequestListener<EntityClass> listener) {
		LocalRequetsImpl.loadEntities(entities);
		for(EntityClass entity : entities)
			listener.onNewResult(entity);
		listener.onFinished(entities);
	}

	@Override
	public <EntityClass extends Entity> void saveEntities(Collection<EntityClass> entities, RequestListener<EntityClass> listener) {
		LocalRequetsImpl.saveEntities(entities);
		for(EntityClass entity : entities)
			listener.onNewResult(entity);
		listener.onFinished(entities);
	}

	@Override
	public void getIdsByType(TypeName type,
			RequestListener<Long> listener) {
		Collection<Long> results = LocalRequetsImpl.getIdsByType(type);
		for(Long result : results)
			listener.onNewResult(result);
		listener.onFinished(results);
	}

	@Override
	public void doLogin(String username, String password, RequestListener<Long> listener) {
		throw new RuntimeException("Method doLogin() is not available server side.");
	}

}
