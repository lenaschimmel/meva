package de.gmino.meva.shared;

import org.itemscript.core.values.JsonValue;

public class ValueWrapper {
	
	public TypeName getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	// May be a Meva entity, a Meva value, a String or a Java wrapper type like "Integer".
	Object value;
	
	TypeName type;
	String name;
	String description;
	
	public int getInt()
	{
		if(!(type == TypeName.Integer))
			throw new RuntimeException("This value has type " + type + ", Integer needed.");
		return ((Integer)value).intValue();
	}
	
	public boolean getBoolean()
	{
		if(!(type == TypeName.Boolean))
			throw new RuntimeException("This value has type " + type + ", Boolean needed.");
		return ((Boolean)value).booleanValue();
	}
	
	public float getFloat()
	{
		if(!(type == TypeName.Float))
			throw new RuntimeException("This value has type " + type + ", Float needed.");
		return ((Float)value).floatValue();
	}
	
	public double getDouble()
	{
		if(!(type == TypeName.Double))
			throw new RuntimeException("This value has type " + type + ", Double needed.");
		return ((Double)value).doubleValue();
	}
		
	public long getLong()
	{
		if(!(type == TypeName.Long))
			throw new RuntimeException("This value has type " + type + ", Long needed.");
		return ((Long)value).longValue();
	}
	
	public String getString()
	{
		if(!(type == TypeName.String))
			throw new RuntimeException("This value has type " + type + ", String needed.");
		return (String)value;
	}
	
	public Entity getEntity()
	{
		if(type.isEntity())
			throw new RuntimeException("This value has type " + type + ", some Entity needed.");
		return (Entity)value;
	}
	
	
	public Value getValue(){
		if(type.isValue())
			throw new RuntimeException("This value has type " + type + ", some Value needed.");
		return (Value)value;
	}

	public void setInt(int v)
	{
		if(!(type == TypeName.Integer))
			throw new RuntimeException("This value has type " + type + ", Integer needed.");
		value = v;
	}
	
	public void setBoolean(boolean v)
	{
		if(!(type == TypeName.Boolean))
			throw new RuntimeException("This value has type " + type + ", Boolean needed.");
		value = v;
	}
	
	public void setFloat(float v)
	{
		if(!(type == TypeName.Float))
			throw new RuntimeException("This value has type " + type + ", Float needed.");
		value = v;
	}
	
	public void setDouble(double v)
	{
		if(!(type == TypeName.Double))
			throw new RuntimeException("This value has type " + type + ", Double needed.");
		value = v;
	}
		
	public void setLong(long v)
	{
		if(!(type == TypeName.Long))
			throw new RuntimeException("This value has type " + type + ", Long needed.");
		value = v;
	}
	
	public void setString(String v)
	{
		if(!(type == TypeName.String))
			throw new RuntimeException("This value has type " + type + ", String needed.");
		value = v;
	}
	
	public void setEntity(Entity v)
	{
		if(type != v.getType())
			throw new RuntimeException("This value has type " + type + ", " + v.getTypeName() + " needed.");
		value = v;
	}
	
	
	public ValueWrapper(TypeName type, String name, String description) {
		super();
		this.type = type;
		this.name = name;
		this.description = description;
	}

	public void setValue(Value v) {
		if(type != v.getType())
			throw new RuntimeException("This value has type " + type + ", " + v.getTypeName() + " needed.");
		value = v;
	}
	
	public String getJson() {
		if(type.isValue())
			return getValue().toString();
		else if(type.isEntity())
			return getEntity().getId() + "";
		else if(type == TypeName.String)
			return "\""+getString() + "\"";
		else
			return value.toString();
	}
	
	public void setJson(JsonValue json) {
		if(type.isValue())
			EntityFactory.createValueObjectFromJson(type, json.asObject());
		else if(type.isEntity())
			EntityFactory.getUnloadedEntityById(type, json.asNumber().longValue());
		else if(type == TypeName.String)
			setString(json.asString().stringValue());
		else if(type == TypeName.Integer)
			setInt(json.asNumber().intValue());
		else if(type == TypeName.Long)
			setLong(json.asNumber().longValue());
		else if(type == TypeName.Float)
			setFloat(json.asNumber().floatValue());
		else if(type == TypeName.Double)
			setDouble(json.asNumber().doubleValue());
	}
}