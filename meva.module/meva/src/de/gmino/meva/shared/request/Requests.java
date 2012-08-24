package de.gmino.meva.shared.request;

import java.util.Collection;

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.Query;

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
			throw new RuntimeException(
					" Requests.setImplementation must be called before using any of its other methods.");
	}

	public static void getIdsByQuery(Query q, RequestListener<Long> listener) {
		
		ensureImplementation();
		
		networkImpl.getIdsByQuery(q, listener);
	}

	public static <EntityClass extends Entity> void getLoadedEntitiesById(
			EntityTypeName type, Collection<Long> ids,
			RequestListener<EntityClass> listener) {

		ensureImplementation();
		
		Collection<EntityClass> entities = EntityFactory.getUnloadedEntitiesById(type, ids);
		loadEntities(entities, listener);
	}

	public static <EntityClass extends Entity> void getLoadedEntitiesByQuery(
			final EntityTypeName type, Query q,
			final RequestListener<EntityClass> listener) {

		ensureImplementation();

		getIdsByQuery(q, new RequestListener<Long>() {
			@Override
			public void onFinished(Collection<Long> ids) {
				Collection<EntityClass> entities = EntityFactory
						.getUnloadedEntitiesById(type, ids);
				loadEntities(entities, listener);

			}
		});
	}

	public static <EntityClass extends Entity> void getNewEntities(
			final EntityTypeName type, final int count,
			final RequestListener<EntityClass> listener) {

		ensureImplementation();

		getNewIds(type, count, new RequestListener<Long>() {
			@Override
			public void onFinished(Collection<Long> ids) {
				Collection<EntityClass> entitites = EntityFactory
						.getUnloadedEntitiesById(type, ids);
				listener.onFinished(entitites);
			}
		});
	}

	public static void getNewIds(EntityTypeName type, int count,
			RequestListener<Long> listener) {

		ensureImplementation();

		networkImpl.getNewIds(type, count, listener);
	}

	public static <EntityClass extends Entity> void getUnloadedEntitiesByQuery(
			final EntityTypeName type, Query q,
			final RequestListener<EntityClass> listener) {

		ensureImplementation();

		getIdsByQuery(q, new RequestListener<Long>() {
			@Override
			public void onFinished(Collection<Long> ids) {
				Collection<EntityClass> entities = EntityFactory
						.getUnloadedEntitiesById(type, ids);
				listener.onFinished(entities);
			}
		});
	}

	public static <EntityClass extends Entity> void loadEntities(
			Collection<EntityClass> entities,
			RequestListener<EntityClass> listener) {

		ensureImplementation();

		networkImpl.loadEntities(entities, listener);
	}

	public static <EntityClass extends Entity> void saveEntities(
			Collection<EntityClass> entities,
			RequestListener<EntityClass> listener) {

		ensureImplementation();

		networkImpl.saveEntities(entities, listener);
	}

}
