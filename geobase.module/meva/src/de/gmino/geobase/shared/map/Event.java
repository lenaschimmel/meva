package de.gmino.geobase.shared.map;

/**
 * Names are currently identical to the ones used in OpenLayers, which are all
 * lowercase.
 * 
 * @author lena
 * 
 */
public enum Event {
	mouseover, mouseout, mousedown, mouseup, mousemove, click, dblclick, rightclick, dblrightclick, resize, focus, blur, touchstart, touchmove, touchend, keydown,

	// not directly supported by openlayers:
	move(true), zoom(true);

	private boolean changesView;

	Event(boolean changesView) {
		this.changesView = changesView;
	}

	Event() {
		this.changesView = false;
	}

	public boolean isChangingView() {
		return changesView;
	}
}
