

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/meva/src/de/gmino/geobase/shared/map/MapListener.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

package de.gmino.geobase.shared.map;

import de.gmino.geobase.shared.domain.LatLon;

public interface MapListener {
	void onEvent(LatLon location, Event event);
}
