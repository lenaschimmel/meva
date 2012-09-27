package de.gmino.checkin.server.request;

import java.util.Collection;

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.ValueQuery;
import de.gmino.meva.shared.request.NetworkRequests;
import de.gmino.meva.shared.request.RequestListener;

/**
 * On the server, there aren't any real asynchronous requests. Therefore, this
 * class just delegates to its synchronous companion,
 * LocalRequetsImpl and acts as if this was done asynchronously.
 * 
 * @author lena
 * 
 */
public class NetworkRequestsImplAsyncLocalSql implements NetworkRequests {

	@Override
	public void getIdsByQuery(EntityQuery query, RequestListener<Long> listener) {
		listener.onFinished(query.evaluate());
	}
	
	@Override
	public void getValuesByQuery(ValueQuery query, RequestListener<Value> listener) {
		listener.onFinished(query.evaluate());
	}

	@Override
	public void getNewIds(EntityTypeName type, int count,
			RequestListener<Long> listener) {
		listener.onFinished(LocalRequetsImpl.getNewIds(type,
				count));
	}

	@Override
	public <EntityClass extends Entity> void loadEntities(
			Collection<EntityClass> entities,
			RequestListener<EntityClass> listener) {
		LocalRequetsImpl.loadEntities(entities);
		listener.onFinished(entities);
	}

	@Override
	public <EntityClass extends Entity> void saveEntities(
			Collection<EntityClass> entities,
			RequestListener<EntityClass> listener) {
		LocalRequetsImpl.saveEntities(entities);
		listener.onFinished(entities);
	}

}
