package de.gmino.issuemap.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.itemscript.core.values.JsonObject;

import de.gmino.issuemap.server.domain.Comment;
import de.gmino.issuemap.server.domain.DecentralizedGeneration;
import de.gmino.issuemap.server.domain.Issue;
import de.gmino.issuemap.server.domain.Map;
import de.gmino.issuemap.server.domain.Markertype;
import de.gmino.issuemap.server.domain.Photo;
import de.gmino.issuemap.server.domain.Route;
import de.gmino.issuemap.server.request.QueryMapBySubdomain;
import de.gmino.issuemap.server.request.SendFeedback;
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
		if (typeName.equals("Comment"))
			return new Comment(id);
		if (typeName.equals("Photo"))
			return new Photo(id);
		if (typeName.equals("Route"))
			return new Route(id);
		if (typeName.equals("DecentralizedGeneration"))
			return new DecentralizedGeneration(id);
		throw new RuntimeException("Unsupported Entity type: " + typeName);
	}

	@Override
	public Object createQueryObject(String typeName, Object request) throws IOException {
		if (request instanceof JsonObject)
			return createQueryObjectFromJson(typeName, (JsonObject)request);
		else if (request instanceof DataInputStream)
			return createQueryObjectFromStream(typeName, (DataInputStream)request);
		else throw new RuntimeException("requestObject must either be an instace of JsonObject or an instance of DataInputStream.");
	}
	
	private Object createQueryObjectFromJson(String typeName, JsonObject request) throws IOException {
		if (typeName.equals("QueryMapBySubdomain"))
			return new QueryMapBySubdomain(request);
		else if (typeName.equals("SendFeedback"))
			return new SendFeedback(request);
		else
			throw new RuntimeException("Unrecognized query type: " + typeName);
	}

	private Object createQueryObjectFromStream(String typeName, DataInputStream request) throws IOException {
		if (typeName.equals("QueryMapBySubdomain"))
			return new QueryMapBySubdomain(request);
		else if (typeName.equals("SendFeedback"))
			return new SendFeedback(request);
		else
			throw new RuntimeException("Unrecognized query type: " + typeName);
	}

}
