// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.request;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.itemscript.core.values.JsonObject;

import de.gmino.issuemap.server.domain.Map;
import de.gmino.issuemap.server.request.gen.SendFeedbackGen;
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces
// imports for mail
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
			String emailAddress,
			Map map)
	{
		super(
			toDevelopers,
			message,
			emailAddress,
			(de.gmino.issuemap.server.domain.Map)map
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
	
	@Override
	public Collection<? extends Value> evaluate() {
		
		
		Properties props = new Properties();
        final Session session = Session.getDefaultInstance(props, null);

        Requests.loadEntity(map, new RequestListener<de.gmino.issuemap.shared.domain.Map>() {
        	@Override
        	public void onNewResult(de.gmino.issuemap.shared.domain.Map result) {
        		Logger logger = Logger.getLogger("SendFeedback");
        		logger.setLevel(Level.ALL);
        		 try {
        			 
        	            Message msg = new MimeMessage(session);

        	            logger.info("Trying to send mail, content: " + message);

        	            msg.setFrom(new InternetAddress("greenmobileinnovations@gmail.com ", "greenmobile Innovations Geoengine - Karte " + map.getTitle()));
        	            if(toDevelopers)
        	            	msg.addRecipient(Message.RecipientType.TO,
        	                        new InternetAddress("info@gmino.de", "Geoengine-Zuständiger"));
        	            else
        	            	msg.addRecipient(Message.RecipientType.TO,
        	                    new InternetAddress(map.getEmail(), map.getPostal_address().getRecipientName()));

        	            msg.setSubject("Feedback zur Geoengine-Karte " + map.getTitle());
        	     
        	            String user = (emailAddress != null && emailAddress.length() > 3) ? "Ein Nutzer mit der (nicht verifizierten) Adresse " + emailAddress : "Ein annonymer Nutzer";
        	            String addressat = toDevelopers ? "Hallo Gminos" : "Sehr gehrte " + map.getPostal_address().getRecipientName();
        	            	
        	            String fullMessage = addressat + ",\n" + user + " hat auf der Geoengine-Seite " + map.getSubdomain() + ".geoengine.de den folgenden Kommentar für sie verfasst:\n\n" + message;
        	            
        	            msg.setText(fullMessage);
        	            Transport.send(msg);
        	            
        	            logger.info("Mail has been sent, content: " + fullMessage);
        	    
        	        } catch (Exception e) {
        	            e.printStackTrace();
        	            logger.log(Level.SEVERE, "Error sending mail.", e);

        	        }
        	}
		});
        
       
		
		return new LinkedList<de.gmino.meva.shared.domain.Void>();
	}
}
