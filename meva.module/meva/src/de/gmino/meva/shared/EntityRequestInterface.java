package de.gmino.meva.shared;

import java.util.Collection;

/**
 * Retrieval of entity ids or entity contents - classes that implement this
 * interface do not create new entity objects. Different implementations will
 * use different ways to either evaluate the request locally, or to send thode
 * requests across the network to get a result.
 * 
 * @author lena
 * 
 */
public interface EntityRequestInterface {
	void loadEntities(Collection<Entity> c);
	void loadEntity(Entity e);
	Collection<Long> getNewEntities(String typeName, int count);
}
