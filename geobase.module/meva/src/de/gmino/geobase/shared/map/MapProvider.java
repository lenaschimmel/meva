package de.gmino.geobase.shared.map;

public enum MapProvider {

	MAPNIK("mapnik", "OSM (Mapnik)"), 
	OPEN_CYCLE_MAP("opencyclemap", "Open Cycle Map"), 
	MAPQUEST("mapquest", "Mapquest"), 
	MAPQUEST_AERIAL("mapquestarial", "Mapquest Aerial"), 
	TRANSPORT("transport", "Transport"), 
	LANDSCAPE("landscape", "Landschaft"), 
	NO_LABELS("nolabels", "Ohne Text"), 
	HIKE_BIKE("hikebike", "Hike & Bike"), 
	OPEN_STREET_BROWSER("openstreetbrowser", "Open Street Browser"), 
	OE_P_N_V("oepnv", "ÖPNV"), 
	WANDER_REIT("wanderreit", "Wander- & Reitkarte");

	public String getUrlName() {
		return urlName;
	}

	public String getReadableName() {
		return readableName;
	}

	private String urlName;
	private String readableName;

	MapProvider(String urlName, String readableName) {
		this.urlName = urlName;
		this.readableName = readableName;
	}
}
