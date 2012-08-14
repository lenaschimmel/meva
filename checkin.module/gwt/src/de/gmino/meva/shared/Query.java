

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/gwt/src/de/gmino/meva/shared/Query.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.



// DONTEDIT This file has been copied from /home/lena/workspaceNeu/meva.module/meva/src/de/gmino/meva/shared/Query.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

package de.gmino.meva.shared;

import java.util.Collection;

public interface Query extends Value {
	public Collection<Long> evaluate();
	
	public String getUrlPostfix();
}
