package de.gmino.checkin.server;

import java.util.Collection;

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityRequestInterface;

public class LocalRequestFoo implements EntityRequestInterface {

	@Override
	public void loadEntities(Collection<Entity> c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadEntity(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Long> getNewEntities(String typeName, int count) {
		// TODO Auto-generated method stub
		return null;
	}

}
