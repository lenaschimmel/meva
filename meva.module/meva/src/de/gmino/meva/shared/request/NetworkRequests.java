package de.gmino.meva.shared.request;

import java.util.Collection;

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.Query;

public interface NetworkRequests {
	public void getIdsByQuery(Query query, RequestListener<Long> listener);

	public void getNewIds(EntityTypeName type, int count,
			RequestListener<Long> listener);

	public <EntityClass extends Entity> void loadEntities(
			Collection<EntityClass> entities,
			RequestListener<EntityClass> listener);

	public <EntityClass extends Entity> void saveEntities(
			Collection<EntityClass> entities,
			RequestListener<EntityClass> listener);

}
