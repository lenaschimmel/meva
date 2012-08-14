package de.gmino.checkin.android;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityRequestInterface;

/**
 * This class is used by EntityFactory to perform actual requests to a server.
 * @author lena
 *
 */
public class EntityRequestBinary implements EntityRequestInterface {

	String baseUrl;
	
	public EntityRequestBinary(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	@Override
	public void loadEntities(Collection<Entity> c) {
		if(c.isEmpty())
			return;
		// TODO actual request
	}

	@Override
	public void loadEntity(Entity e) {
		Collection<Entity> c = new LinkedList<Entity>();
		c.add(e);
		loadEntities(c);
	}

	@Override
	public Collection<Long> getNewEntities(String typeName, int count) {
		Collection<Long> ret = new ArrayList<Long>(count);
		if(count == 0)
			return ret;
		// TODO actual request
		return ret;
	}


}
