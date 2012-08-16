package de.gmino.codegen;

public class AttributeDefiniton {
	String attributeName;
	String typeName;
	public int maxLen = 1000;
	public String relname;
	public String reltype;
	
	public AttributeDefiniton(String typeName, String attributeName) {
		super();
		this.typeName = typeName;
		this.attributeName = attributeName;
	}
	
	public boolean isEntity()
	{
		boolean attributeIsEntity = false;
		if (isDomainType())
			attributeIsEntity = Meva.getClassDefinition(typeName, true).entity;
		return attributeIsEntity;
	}

	public boolean isDomainType() {
		return !typeName.equals("String")
				&& !typeName.equals("relation")
				&& !Types.nativeTypes.contains(typeName);
	}

	public boolean isNative()
	{
		return Types.nativeTypes.contains(typeName);
	}
	
	public boolean isNativeOrString()
	{
		return typeName.equals("String") || Types.nativeTypes.contains(typeName);
	}

	public boolean isRelation()
	{
		return typeName.equals("relation");
	}
	
	public String getUsableType()
	{
		if(isRelation())
			return "RelationCollection<"+reltype+">";
		else
			return typeName;
	}

	public String getSqlType()
	{
		return Types.sqlTypes.get(typeName);
	}

	public String getWrapperType()
	{
		return Types.wrapperTypes.get(typeName);
	}
	
	public ClassDefinition getClassDefinition()
	{
		return Meva.getClassDefinition(typeName, true);
	}
}
