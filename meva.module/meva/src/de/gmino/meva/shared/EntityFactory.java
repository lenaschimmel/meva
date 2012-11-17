package de.gmino.meva.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Platform independent class that manages local in-memory caching of entities
 * and delegates to platform-dependent implementation if needed.
 * 
 * As you wull see, this class provides synchronous methods (they may either
 * block or fail) and works on a relatively low level (Ids are explicitly
 * mentioned).
 * 
 * Application developers usually only call getNewEntity/ies - all other methods
 * are better avoided by using higher-level abstrations.
 * 
 * @author lena
 * 
 */
public class EntityFactory {
	static EntityFactoryInterface factoryImplementation;
	static Map<String, Map<Long, Entity>> entityMaps;

	public static void registerType(String typeName) {
		if (entityMaps == null)
			entityMaps = new TreeMap<String, Map<Long, Entity>>();
		if (!entityMaps.containsKey(typeName)) {
			entityMaps.put(typeName, new TreeMap<Long, Entity>());
			System.out.println("Registered domain type in EntityFactory: " + typeName);
		}
	}

	public static <EntityClass extends Entity> Collection<EntityClass> getUnloadedEntitiesById(EntityTypeName type, Collection<Long> ids) {
		if (factoryImplementation == null)
			throw new RuntimeException("You must first call setImplementations.");

		// TODO: This is kind of a hack, but maybe thats ok:
		registerType(type.toString());
		Map<Long, Entity> mapForThisType = entityMaps.get(type.toString());

		// if (mapForThisType == null)
		// throw new RuntimeException("Type '"+typeName+"' not supported.");

		Collection<EntityClass> ret = new ArrayList<EntityClass>(ids.size());

		for (Long id : ids) {
			EntityClass e = (EntityClass) mapForThisType.get(id);
			if (e == null) {
				e = (EntityClass) factoryImplementation.createEntityObject(type.toString(), id);
				mapForThisType.put(id, e);
			}
			ret.add(e);
		}

		return ret;
	}

	public static Entity getUnloadedEntityById(EntityTypeName type, long id) {
		// TODO Always delegating to the collection-variant avoids code
		// duplication, but is not the best decision for performance.
		Collection<Long> idList = new LinkedList<Long>();
		idList.add(id);
		Collection<Entity> ret = getUnloadedEntitiesById(type, idList);
		if (ret == null)
			return null;
		return ret.iterator().next();
	}

	public static void setImplementations(EntityFactoryInterface factory) {
		factoryImplementation = factory;
	}
}
