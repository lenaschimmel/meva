package de.gmino.meva.shared;

import java.io.IOException;
import java.util.Collection;

import org.itemscript.core.values.JsonObject;

/**
 * Methods to create entity objects - independant from the creation of the
 * actual entities.
 * 
 * @author lena
 * 
 */
public interface EntityFactoryInterface {
	public Collection<Entity> createEntityObjects(String typeName, Collection<Long> ids);

	public Entity createEntityObject(String typeName, long id);
	
	public Object createQueryObject(String typeName, JsonObject request) throws IOException;
}
