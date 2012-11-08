

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/meva/src/de/gmino/geobase/shared/map/Marker.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

package de.gmino.geobase.shared.map;

import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;

public interface Marker extends Comparable<Marker> {
	String getTitle();
	String getDescription();
	void setLocation(LatLon location);
	LatLon getLocation();
	ImageUrl getIconUrl();
	int getInternalId();
}
