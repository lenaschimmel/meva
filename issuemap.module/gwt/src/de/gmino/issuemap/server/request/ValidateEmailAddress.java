// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.request;

// gmino stuff
import de.gmino.meva.shared.Log;
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.ValueQuery;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.RelationCollection;
import de.gmino.meva.shared.TypeName;
import de.gmino.meva.shared.Util;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.TreeMap;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.TreeSet;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// imports for serialization interfaces
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.ValueBinary;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;
import de.gmino.meva.server.EntitySql;

// imports for field types
import de.gmino.issuemap.server.domain.User;


import de.gmino.issuemap.server.request.gen.ValidateEmailAddressGen;
@SuppressWarnings("unused")
public class ValidateEmailAddress extends ValidateEmailAddressGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public ValidateEmailAddress()
	{
	}

	// Constructor for SQL deseralizaiton
	public ValidateEmailAddress(String prefix, ResultSet rs) throws SQLException
	{
		super(prefix, rs);
	}
	public ValidateEmailAddress(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public ValidateEmailAddress(JsonObject json) throws IOException
	{
		super(json);
	}
	public ValidateEmailAddress(
			User user)
	{
		super(
			(de.gmino.issuemap.server.domain.User)user
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
	
	@Override
	public Collection<? extends Value> evaluate() {
		Requests.loadEntity(user, new RequestListener<de.gmino.issuemap.shared.domain.User>() {
        	@Override
        	public void onNewResult(de.gmino.issuemap.shared.domain.User result) {
        		 try {
        			 	Properties props = new Properties();
        		        final Session session = Session.getDefaultInstance(props, null);
        			 
        	            Message msg = new MimeMessage(session);

        	            Log.log("Trying to send validation mail.");

        	            msg.setFrom(new InternetAddress("greenmobileinnovations@gmail.com ", "greenmobile Innovations Geoengine"));
        	            msg.addRecipient(Message.RecipientType.TO,
        	                new InternetAddress(user.getEmail(), user.getUserName()));

        	            msg.setSubject("Verifikation ihrer Emailadresse bei Geoengine");
        	     
        	            String fullMessage = "Hallo " + user.getUserName() + ",\nSie haben sich gerade auf der Seite geoengine.de angemeldet. Bitte bestätigen Sie Ihre Email-Adresse, indem sie die folgende Seite aufrufen: bla.";
        	            
        	            msg.setText(fullMessage);
        	            Transport.send(msg);
        	            
        	            Log.log("Mail has been sent, content: " + fullMessage);
        	    
        	        } catch (Exception e) {
        	            Log.exception("Error sending mail.", e);
        	        }
        	}
		});
		
		return new LinkedList<de.gmino.meva.shared.domain.Void>();
	}
}
