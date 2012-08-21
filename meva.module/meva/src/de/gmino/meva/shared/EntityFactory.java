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
	static EntityRequestInterface requestImplementation;
	static Map<String, Map<Long, Entity>> entityMaps;

	public static void registerType(String typeName) {
		if (entityMaps == null)
			entityMaps = new TreeMap<String, Map<Long, Entity>>();
		if (!entityMaps.containsKey(typeName)) {
			entityMaps.put(typeName, new TreeMap<Long, Entity>());
			System.out.println("Registered domain type in EntityFactory: "
					+ typeName);
		}
	}

	public static Collection<Entity> getEntitiesById(String typeName,
			Collection<Long> ids, ReturnEntityPolicy policy) {
		if (factoryImplementation == null || requestImplementation == null)
			throw new RuntimeException(
					"You must first call setImplementations.");

		// TODO: This is kind of a hack, but maybe thats ok:
		registerType(typeName);
		Map<Long, Entity> mapForThisType = entityMaps.get(typeName);

		// if (mapForThisType == null)
		// throw new RuntimeException("Type '"+typeName+"' not supported.");

		Collection<Entity> ret = new ArrayList<Entity>(ids.size());
		Collection<Entity> entitiesToFetch = new LinkedList<Entity>();

		for (Long id : ids) {
			Entity e = mapForThisType.get(id);
			if (e == null) {
				e = factoryImplementation.createEntityObject(typeName, id);
				mapForThisType.put(id, e);
			}
			if (!e.isReady())
				entitiesToFetch.add(e);
			ret.add(e);
		}

		// no need to load here -> policy will do this if requested
//		if (!entitiesToFetch.isEmpty())
	//		requestImplementation.loadEntities(entitiesToFetch);

		return policy.performAction(ret);
	}

	public static Entity getEntityById(String typeName, long id,
			ReturnEntityPolicy policy) {
		// TODO Always delegating to the collection-variant avoids code
		// duplication, but is not the best decision for performance.
		Collection<Long> idList = new LinkedList<Long>();
		idList.add(id);
		Collection<Entity> ret = getEntitiesById(typeName, idList, policy);
		if (ret == null)
			return null;
		return ret.iterator().next();
	}

	public static Entity getNewEntity(String typeName) {
		return getNewEntities(typeName, 1).iterator().next();
	}

	public static Collection<Entity> getNewEntities(String typeName, int count) {
		if (factoryImplementation == null)
			throw new RuntimeException(
					"You must first call setImplementations.");

		// TODO: This is kind of a hack, but maybe thats ok:
		registerType(typeName);

		Map<Long, Entity> mapForThisType = entityMaps.get(typeName);
		if (mapForThisType == null)
			throw new RuntimeException("Type not supported.");

		final Collection<Long> ids = requestImplementation.getNewEntities(
				typeName, count);
		Collection<Entity> ret = factoryImplementation.createEntityObjects(
				typeName, ids);

		for (Entity e : ret)
			mapForThisType.put(e.getId(), e);
		return ret;
	}

	public static void setImplementations(EntityFactoryInterface factory,
			EntityRequestInterface request) {
		factoryImplementation = factory;
		requestImplementation = request;
	}

	public static void loadEntities(Collection<Entity> c) {
		if (requestImplementation == null)
			throw new RuntimeException(
					"You must first call setImplementations.");
		requestImplementation.loadEntities(c);
	}

	public static void loadEntity(Entity e) {
		if (requestImplementation == null)
			throw new RuntimeException(
					"You must first call setImplementations.");
		requestImplementation.loadEntity(e);
	}


	public static void saveEntities(Collection<Entity> c) {
		if (requestImplementation == null)
			throw new RuntimeException(
					"You must first call setImplementations.");
		requestImplementation.saveEntities(c);
	}
	
	public static void saveEntity(Entity e) {
		if (requestImplementation == null)
			throw new RuntimeException(
					"You must first call setImplementations.");
		requestImplementation.saveEntity(e);
	}

}
