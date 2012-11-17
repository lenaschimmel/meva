// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.android.domain;

// gmino stuff
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.RelationCollection;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.Util;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

// imports for serialization interfaces
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.ValueBinary;

// imports for field types
import de.gmino.geobase.android.domain.ImageUrl;
import de.gmino.geobase.android.domain.LatLon;

import de.gmino.geobase.android.domain.gen.GeoObjectGen;

public class GeoObject extends GeoObjectGen {
	// Constructors
	public GeoObject(long id) {
		super(id);
	}

	public GeoObject(long id, boolean ready, LatLon location, String title, String description, ImageUrl markerImage) {
		super(id, ready, (de.gmino.geobase.android.domain.LatLon) location, title, description, (de.gmino.geobase.android.domain.ImageUrl) markerImage);
		this.ready = true;
	}

}
