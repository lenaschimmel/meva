package de.gmino.meva.shared.request;

import java.util.Collection;
import java.util.LinkedList;

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.ValueQuery;

/**
 * This class contains methods for all kinds of request that an application will
 * perform to work with entities.
 * 
 * All of those methods return nothing (void) but take a RequestResultListener
 * with callbacks. Your callbacks are called with the result of the request as a
 * parameter.
 * 
 * Most methods are generic. Its absolutely easy to use them, but the syntax
 * might confuse you if you aren't used to generic methods. See
 * http://docs.oracle.com/javase/tutorial/extra/generics/methods.html for more
 * information.
 * 
 * Note that there are both high-level- and low-level-requests in this class.
 * The method documentation of each request will usually indicate whether this
 * is a high- or low-level one, but there's a general rule, even though the
 * distinction is somewhat blurry:
 * 
 * The low-level ones are used internally by the high-level-ones. Applications
 * will usually use high level requests that work with Entity objects and Query
 * objects, but will not call low-level requests that operate on ids. Also, if
 * you use the result of a request to instantly perform another request, chances
 * are high that you could do both with a higher-level-request.
 * 
 * @author lena
 * 
 */
public class Requests {
	private static NetworkRequests networkImpl;

	public static void setImplementation(NetworkRequests networkImpl) {
		Requests.networkImpl = networkImpl;
	}

	private static void ensureImplementation() {
		if (networkImpl == null)
			throw new RuntimeException(" Requests.setImplementation must be called before using any of its other methods.");
	}

	public static void getIdsByQuery(EntityQuery q, RequestListener<Long> listener) {
		ensureImplementation();
		networkImpl.getIdsByQuery(q, listener);
	}

	public static void getValuesByQuery(ValueQuery query, RequestListener<? extends Value> listener) {
		ensureImplementation();
		networkImpl.getValuesByQuery(query, listener);
	}

	public static <EntityClass extends Entity> void getLoadedEntityById(EntityTypeName type, long id, RequestListener<EntityClass> listener) {
		Collection<Long> list = new LinkedList<Long>();
		list.add(id);
		getLoadedEntitiesById(type, list, listener);
	}

	public static <EntityClass extends Entity> void getLoadedEntitiesById(EntityTypeName type, Collection<Long> ids, RequestListener<EntityClass> listener) {
		ensureImplementation();
		
		Collection<EntityClass> entities = EntityFactory.getUnloadedEntitiesById(type, ids);
		loadEntities(entities, listener);
	}
	
	public static <EntityClass extends Entity> void getLoadedEntitiesByType(EntityTypeName type, final RequestListener<EntityClass> listener) {
		ensureImplementation();

		getUnloadedEntitiesByType(type, new RequestListener<EntityClass>() {
			@Override
			public void onFinished(Collection<EntityClass> entities) {
				loadEntities(entities, listener);
			}
		});
	}	
	
	public static <EntityClass extends Entity> void getUnloadedEntitiesByType(final EntityTypeName type, final RequestListener<EntityClass> listener) {
		ensureImplementation();

		networkImpl.getIdsByType(type, new RequestListener<Long>() {
			@Override
			public void onFinished(Collection<Long> ids) {
				Collection<EntityClass> ret = EntityFactory.getUnloadedEntitiesById(type, ids);
				listener.onFinished(ret);
			}
		});
		
	}

	public static <EntityClass extends Entity> void getLoadedEntitiesByQuery(final EntityTypeName type, EntityQuery q, final RequestListener<EntityClass> listener) {
		ensureImplementation();

		getIdsByQuery(q, new RequestListener<Long>() {
			@Override
			public void onFinished(Collection<Long> ids) {
				Collection<EntityClass> entities = EntityFactory.getUnloadedEntitiesById(type, ids);
				loadEntities(entities, listener);

			}

			@Override
			public void onError(String message, Throwable e) {
				listener.onError(message, e);
			}

		});
	}

	public static <EntityClass extends Entity> void getNewEntity(final EntityTypeName type, final RequestListener<EntityClass> listener) {
		getNewEntities(type, 1, listener);
	}

	public static <EntityClass extends Entity> void getNewEntities(final EntityTypeName type, final int count, final RequestListener<EntityClass> listener) {
		ensureImplementation();

		getNewIds(type, count, new RequestListener<Long>() {
			@Override
			public void onFinished(Collection<Long> ids) {
				Collection<EntityClass> entitites = EntityFactory.getUnloadedEntitiesById(type, ids);
				listener.onFinished(entitites);
			}

			@Override
			public void onNewResult(Long result) {
				EntityClass e = (EntityClass) EntityFactory.getUnloadedEntityById(type, result);
				listener.onNewResult(e);
			}

			@Override
			public void onError(String message, Throwable e) {
				listener.onError(message, e);
			}
		});
	}

	public static void getNewIds(EntityTypeName type, int count, RequestListener<Long> listener) {

		ensureImplementation();

		networkImpl.getNewIds(type, count, listener);
	}

	public static <EntityClass extends Entity> void getUnloadedEntitiesByQuery(final EntityTypeName type, EntityQuery q, final RequestListener<EntityClass> listener) {

		ensureImplementation();

		getIdsByQuery(q, new RequestListener<Long>() {
			@Override
			public void onFinished(Collection<Long> ids) {
				Collection<EntityClass> entities = EntityFactory.getUnloadedEntitiesById(type, ids);
				listener.onFinished(entities);
			}
		});
	}

	public static <EntityClass extends Entity> void loadEntity(EntityClass entity, RequestListener<EntityClass> listener) {
		Collection<EntityClass> entities = new LinkedList<EntityClass>();
		entities.add(entity);
		loadEntities(entities, listener);
	}

	/**
	 * This is the only requests that accepts null as a listener. Be warned
	 * though: if no listener is passed an and error occurs, a Runtime exception
	 * will be thrown, possibly on the same or another thread as the one that
	 * started the request.
	 * 
	 * @param entities
	 * @param listener
	 *            A listener or null.
	 */
	public static <EntityClass extends Entity> void saveEntity(EntityClass entity, RequestListener<EntityClass> listener) {

		Collection<EntityClass> entities = new LinkedList<EntityClass>();
		entities.add(entity);
		saveEntities(entities, listener);
	}

	public static <EntityClass extends Entity> void loadEntities(Collection<EntityClass> entities, RequestListener<EntityClass> listener) {

		ensureImplementation();
		ensureSameTypes(entities);
		networkImpl.loadEntities(entities, listener);
	}

	/**
	 * This is the only requests that accepts null as a listener. Be warned
	 * though: if no listener is passed an and error occurs, a Runtime exception
	 * will be thrown, possibly on the same or another thread as the one that
	 * started the request.
	 * 
	 * @param entities
	 * @param listener
	 *            A listener or null.
	 */
	public static <EntityClass extends Entity> void saveEntities(Collection<EntityClass> entities, RequestListener<EntityClass> listener) {

		ensureImplementation();
		ensureSameTypes(entities);
		networkImpl.saveEntities(entities, listener);
	}

	public static void ensureSameTypes(Collection<? extends Entity> entities) {
		if (entities.isEmpty()) {
			System.err.println("Warning: ensureSameTypes on an empty collection.");
			//new Throwable().printStackTrace();
			return;
		}
		EntityTypeName fistTypeName = entities.iterator().next().getType();
		for (Entity e : entities) {
			if (fistTypeName != e.getType())
				throw new RuntimeException("Heterogenous types in Request: first Entity has type " + fistTypeName + ", another one has " + e.getType());
		}
	}
}
