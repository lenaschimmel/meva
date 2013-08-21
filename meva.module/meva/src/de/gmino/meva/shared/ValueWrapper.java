package de.gmino.meva.shared;

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

	public void setValue(Value v){
		if(type != v.getType())
			throw new RuntimeException("This value has type " + type + ", " + v.getTypeName() + " needed.");
		value = v;
	}
}