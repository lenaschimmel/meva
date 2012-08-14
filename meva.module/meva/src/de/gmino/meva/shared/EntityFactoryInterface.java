package de.gmino.meva.shared;

import java.util.Collection;
 
/**
 * Methods to create entity objects - independant from the creation of the actual entities.
 * @author lena
 *
 */
public interface EntityFactoryInterface {
	public Collection<Entity> createEntityObjects(String typeName, Collection<Long> ids);
	public Entity createEntityObject(String typeName, long id);
}
