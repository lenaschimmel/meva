// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.ReturnEntityPolicy;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

// imports for field types
import de.gmino.geobase.shared.domain.Date;


import de.gmino.geobase.shared.domain.gen.DateSpanGen;
public class DateSpan extends DateSpanGen {
	// Constructors
	public DateSpan(
			Date start,
			Date end)
	{
		super(
			(de.gmino.geobase.shared.domain.Date)start,
			(de.gmino.geobase.shared.domain.Date)end
		);
	}
	

}
