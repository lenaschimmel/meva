package de.gmino.geobase.android.map;

import java.util.Map;

import de.gmino.geobase.shared.map.MapProvider;

public class OsmdroidTileSources {

	private static Map<MapProvider, OsmdroidMapLayer> map;
	
	public static OsmdroidMapLayer getSourceByProvider(MapProvider provider) {
		OsmdroidMapLayer ret = map.get(provider);
		if (ret == null) {
			ret = createNewTileSource(provider);
			map.put(provider, ret);
		}
		return ret;
	}

	private static OsmdroidMapLayer createNewTileSource(MapProvider provider) {
		return new OsmdroidMapLayer(provider.getUrlName(), provider.getReadableName());
	}

}
