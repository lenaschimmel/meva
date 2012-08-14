

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/gwt/src/de/gmino/meva/shared/EntityFactoryInterface.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.



// DONTEDIT This file has been copied from /home/lena/workspaceNeu/meva.module/meva/src/de/gmino/meva/shared/EntityFactoryInterface.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

package de.gmino.meva.shared;

import java.util.Collection;
 
/**
 * Methods to create entity objects - independant from the creation of the actual entities.
 * @author lena
 *
 */
public interface EntityFactoryInterface {
	public Collection<Entity> createEntityObjects(String typeName, Collection<Long> ids);
	public Entity createEntityObject(String typeName, long id);
}
