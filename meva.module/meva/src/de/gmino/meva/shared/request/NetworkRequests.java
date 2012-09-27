package de.gmino.meva.shared.request;

import java.util.Collection;

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.ValueQuery;

public interface NetworkRequests {
	public void getIdsByQuery(EntityQuery query, RequestListener<Long> listener);
	public <ValueClass extends Value> void getValuesByQuery(ValueQuery query, RequestListener<ValueClass> listener);

	public void getNewIds(EntityTypeName type, int count,
			RequestListener<Long> listener);

	public <EntityClass extends Entity> void loadEntities(
			Collection<EntityClass> entities,
			RequestListener<EntityClass> listener);

	public <EntityClass extends Entity> void saveEntities(
			Collection<EntityClass> entities,
			RequestListener<EntityClass> listener);

}
