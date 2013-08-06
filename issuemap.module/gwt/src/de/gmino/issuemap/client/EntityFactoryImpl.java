package de.gmino.issuemap.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.itemscript.core.values.JsonObject;

import de.gmino.issuemap.client.domain.Comment;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.MapHasMarkertype;
import de.gmino.issuemap.client.domain.Markertype;
import de.gmino.issuemap.client.domain.Photo;
import de.gmino.issuemap.client.domain.Route;
import de.gmino.issuemap.client.request.QueryMapBySubdomain;
import de.gmino.issuemap.client.request.SendFeedback;
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
		if (typeName.equals("Issue"))
			return new Issue(id);
		if (typeName.equals("Map"))
			return new Map(id);
		if (typeName.equals("Markertype"))
			return new Markertype(id);
		if (typeName.equals("MapHasMarkertype"))
			return new MapHasMarkertype(id);
		if (typeName.equals("Comment"))
			return new Comment(id);
		if (typeName.equals("Photo"))
			return new Photo(id);
		if (typeName.equals("Route"))
			return new Route(id);
		throw new RuntimeException("Unsupported Entity type: " + typeName);
	}

	@Override
	public Object createQueryObject(String typeName, Object request) throws IOException {
		if(!(request instanceof JsonObject))
			throw new RuntimeException("On the client side, requestObject must be of type JsonObject.");
		
		if (typeName.equals("QueryMapBySubdomain"))
			return new QueryMapBySubdomain((JsonObject)request);
		else if (typeName.equals("SendFeedback"))
			return new SendFeedback((JsonObject)request);
		else
			throw new RuntimeException("Unrecognized query type: " + typeName);
	}
}
