package de.gmino.geobase.client.map;

import com.google.gwt.core.client.JavaScriptObject;

import de.gmino.geobase.shared.map.MapLayer;

public interface OpenLayersLayer extends MapLayer {
	JavaScriptObject getJso();
}
