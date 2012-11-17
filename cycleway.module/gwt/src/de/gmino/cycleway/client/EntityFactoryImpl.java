package de.gmino.cycleway.client;

import java.util.ArrayList;
import java.util.Collection;

import de.gmino.geobase.server.domain.GeoObject;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactoryInterface;

public class EntityFactoryImpl implements EntityFactoryInterface {

	@Override
	public Collection<Entity> createEntityObjects(String typeName, Collection<Long> ids) {
		Collection<Entity> ret = new ArrayList<Entity>(ids.size());
		for (long id : ids) {
			ret.add(createEntityObject(typeName, id));
		}
		return ret;
	}

	// TODO autocreate this class, at least the method below

	@Override
	public Entity createEntityObject(String typeName, long id) {
		if (typeName.equals("GeoObject"))
			return new GeoObject(id);
		throw new RuntimeException("Unsupported Entity type: " + typeName);
	}

}
