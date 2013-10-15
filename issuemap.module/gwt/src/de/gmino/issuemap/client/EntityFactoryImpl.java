package de.gmino.issuemap.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.client.domain.Address;
import de.gmino.geobase.client.domain.Distance;
import de.gmino.geobase.client.domain.Duration;
import de.gmino.geobase.client.domain.ImageUrl;
import de.gmino.geobase.client.domain.LatLon;
import de.gmino.geobase.client.domain.Timestamp;
import de.gmino.issuemap.client.domain.Comment;
import de.gmino.issuemap.client.domain.DecentralizedGeneration;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Markertype;
import de.gmino.issuemap.client.domain.Photo;
import de.gmino.issuemap.client.domain.Poi;
import de.gmino.issuemap.client.domain.Route;
import de.gmino.issuemap.client.domain.User;
import de.gmino.issuemap.client.request.QueryMapBySubdomain;
import de.gmino.issuemap.client.request.SendFeedback;
import de.gmino.issuemap.server.request.ValidateEmailAddress;
import de.gmino.meva.client.domain.Date;
import de.gmino.meva.client.domain.DateTime;
import de.gmino.meva.client.domain.KeyValueDef;
import de.gmino.meva.client.domain.KeyValueSet;
import de.gmino.meva.client.domain.LongText;
import de.gmino.meva.client.domain.ShortText;
import de.gmino.meva.client.domain.Time;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactoryInterface;
import de.gmino.meva.shared.TypeName;
import de.gmino.meva.shared.Value;

public class EntityFactoryImpl implements EntityFactoryInterface {

	public EntityFactoryImpl() {
		try {
			createValueObjectFromJson(null, null);
		} catch (Exception e) {
			// Exception always occurs, but this is no error
		}
	}
	
	@Override
	public Collection<Entity> createEntityObjects(String typeName, Collection<Long> ids) {
		Collection<Entity> ret = new ArrayList<Entity>(ids.size());
		for (long id : ids) {
			ret.add(createEntityObject(typeName, id));
		}
		return ret;
	}

	// TODO autocreate this class, at least the method below

	@Override
	public Entity createEntityObject(String typeName, long id) {
		if (typeName.equals("Poi"))
			return new Poi(id);
		if (typeName.equals("Map"))
			return new Map(id);
		if (typeName.equals("Markertype"))
			return new Markertype(id);
		if (typeName.equals("Comment"))
			return new Comment(id);
		if (typeName.equals("Photo"))
			return new Photo(id);
		if (typeName.equals("Route"))
			return new Route(id);
		if (typeName.equals("KeyValueSet"))
			return new KeyValueSet(id);
		if (typeName.equals("KeyValueDef"))
			return new KeyValueDef(id);
		if (typeName.equals("DecentralizedGeneration"))
			return new DecentralizedGeneration(id);
		if (typeName.equals("User"))
			return new User(id);
		throw new RuntimeException("Unsupported Entity type: " + typeName);
	}

	@Override
	public Object createQueryObject(String typeName, Object request) throws IOException {
		if(!(request instanceof JsonObject))
			throw new RuntimeException("On the client side, requestObject must be of type JsonObject.");
		
		if (typeName.equals("QueryMapBySubdomain"))
			return new QueryMapBySubdomain((JsonObject)request);
		else if (typeName.equals("SendFeedback"))
			return new SendFeedback((JsonObject)request);
		else if (typeName.equals("ValidateEmailAddress"))
			return new ValidateEmailAddress((JsonObject)request);
		else
			throw new RuntimeException("Unrecognized query type: " + typeName);
	}

	@Override
	public Value createValueObjectFromJson(TypeName type, JsonObject json) {
		try {
			if(type == LatLon.type)
				return new LatLon(json);
			if(type == Timestamp.type)
				return new Timestamp(json);
			if(type == Address.type)
				return new Address(json);
			if(type == Distance.type)
				return new Distance(json);
			if(type == Distance.type)
				return new Duration(json);
			if(type == ImageUrl.type)
				return new ImageUrl(json);
			if(type == LongText.type)
				return new LongText(json);
			if(type == ShortText.type)
				return new ShortText(json);
			if(type == Date.type)
				return new Date(json);
			if(type == Time.type)
				return new Time(json);
			if(type == DateTime.type)
				return new DateTime(json);
		} catch (IOException e) {
			throw new RuntimeException("Error while deserializing: " + json);
		}
		throw new RuntimeException("Unsupported Value type: " + type);
	}
}
