package de.gmino.meva.shared;

import java.io.DataInputStream;
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

	/**
	 * 
	 * @param typeName
	 * @param requestSource May be an instance of JsonObject or DataInputStream, but not every implementation supports DataInputStream.
	 * @return
	 * @throws IOException
	 */
	public Object createQueryObject(String typeName, Object requestSource) throws IOException;

	public Value createValueObjectFromJson(TypeName type, JsonObject json);

}
