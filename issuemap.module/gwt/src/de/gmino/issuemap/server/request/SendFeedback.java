// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.request;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.ValueQuery;
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
import java.util.LinkedList;

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

// imports for serialization interfaces
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.ValueBinary;
import de.gmino.meva.server.EntitySql;

// imports for mail
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.server.request.gen.SendFeedbackGen;
public class SendFeedback extends SendFeedbackGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public SendFeedback()
	{
	}

	// Constructor for SQL deseralizaiton
	public SendFeedback(String prefix, ResultSet rs) throws SQLException
	{
		super(prefix, rs);
	}
	public SendFeedback(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public SendFeedback(JsonObject json) throws IOException
	{
		super(json);
	}
	public SendFeedback(
			boolean toDevelopers,
			String message,
			String emailAddress)
	{
		super(
			toDevelopers,
			message,
			emailAddress
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
	
	@Override
	public Collection<? extends Value> evaluate() {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);

            IssuemapGwt.logger.info("Trying to send mail, content: " + message);

            msg.setFrom(new InternetAddress("greenmobileinnovations@gmail.com ", "Greenmobile Geoengine"));
            if(toDevelopers)
            	msg.addRecipient(Message.RecipientType.TO,
                        new InternetAddress("info@gmino.de", "Geoengine-Zuständiger"));
            else
            	msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("info@gmino.de", "Partei-Zuständiger"));

            msg.setSubject("Feedback zur Geoengine");
     
            msg.setText(message);
            Transport.send(msg);
            
            IssuemapGwt.logger.info("Mail has been sent, content: " + message);
    
        } catch (Exception e) {
            e.printStackTrace();
            IssuemapGwt.logger.warning("Error sending mail: " + e.getMessage());

        }
		
		return new LinkedList<de.gmino.meva.shared.domain.Void>();
	}
}
