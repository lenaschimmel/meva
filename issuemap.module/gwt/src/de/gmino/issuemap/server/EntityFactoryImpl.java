package de.gmino.issuemap.server;

import java.util.ArrayList;
import java.util.Collection;

import de.gmino.issuemap.server.domain.Comment;
import de.gmino.issuemap.server.domain.Issue;
import de.gmino.issuemap.server.domain.Map;
import de.gmino.issuemap.server.domain.MapHasMarkertype;
import de.gmino.issuemap.server.domain.Markertype;
import de.gmino.issuemap.server.domain.Photo;
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
		throw new RuntimeException("Unsupported Entity type: " + typeName);
	}

}
