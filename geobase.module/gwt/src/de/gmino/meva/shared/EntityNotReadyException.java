

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/meva.module/meva/src/de/gmino/meva/shared/EntityNotReadyException.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

package de.gmino.meva.shared;

import java.util.Collection;

public class EntityNotReadyException extends RuntimeException {

	public EntityNotReadyException(Entity e) {
		super("Entity was not ready: (" + e.getTypeName() + ":" + e.getId()
				+ ")");
	}

	public EntityNotReadyException(Collection<Entity> c) {
		super(makeText(c));
	}

	private static String makeText(Collection<Entity> c) {
		StringBuffer sb = new StringBuffer(
				"At least on of these entities was not ready:");
		for (Entity e : c)
			sb.append("(" + e.getTypeName() + ":" + e.getId() + ") ");
		return sb.toString();
	}

}
