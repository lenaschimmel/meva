

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/meva.module/meva/src/de/gmino/meva/shared/ReturnEntityPolicy.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

package de.gmino.meva.shared;

import java.util.Collection;
import java.util.LinkedList;

/**
 * These policies apply to methods that return an Entity, when its Id is known but its data may not be present locally.
 * @author lena
 */
public enum ReturnEntityPolicy {
	RETURN_NULL, RETURN_UNLOADED, THROW_EXCEPTION, BLOCK_ALWAYS, BLOCK_IF_NEEDED;
	
	public Entity performAction(Entity e)
	{
		if(e.isReady() || this == RETURN_UNLOADED)
			return e;
		
		if(this == RETURN_NULL)
			return null;
		
		if(this == THROW_EXCEPTION)
			throw new EntityNotReadyException(e);
		
		EntityFactory.loadEntity(e);
		return e;
	}
	
	public Collection<Entity> performAction(Collection<Entity> c)
	{
		if(this == RETURN_UNLOADED)
			return c;
		
		boolean allReady = true;
		for(Entity e : c)
			if(!e.isReady())
			{
				allReady = false;
				break;
			}
		if(allReady)
			return c;
		
		if(this == RETURN_NULL)
			return null;
		
		if(this == THROW_EXCEPTION)
			throw new EntityNotReadyException(c);
		
		Collection<Entity> entitiesToLoad = new LinkedList<Entity>();
		for(Entity e : c)
			if(!e.isReady())
				entitiesToLoad.add(e);
		
		EntityFactory.loadEntities(entitiesToLoad);
		
		return c;
	}
}
