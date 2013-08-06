package de.gmino.codegen;

public class AttributeDefiniton {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributeName == null) ? 0 : attributeName.hashCode());
		result = prime * result + maxLen;
		result = prime * result + ((relname == null) ? 0 : relname.hashCode());
		result = prime * result + ((reltype == null) ? 0 : reltype.hashCode());
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeDefiniton other = (AttributeDefiniton) obj;
		if (attributeName == null) {
			if (other.attributeName != null)
				return false;
		} else if (!attributeName.equals(other.attributeName))
			return false;
		if (maxLen != other.maxLen)
			return false;
		if (relname == null) {
			if (other.relname != null)
				return false;
		} else if (!relname.equals(other.relname))
			return false;
		if (reltype == null) {
			if (other.reltype != null)
				return false;
		} else if (!reltype.equals(other.reltype))
			return false;
		if (typeName == null) {
			if (other.typeName != null)
				return false;
		} else if (!typeName.equals(other.typeName))
			return false;
		return true;
	}

	String containingType;
	String attributeName;
	String typeName;
	public int maxLen = 1000;
	public String relname;
	public String reltype;
	public boolean multipleRelation;

	public AttributeDefiniton(String typeName, String attributeName) {
		super();
		this.typeName = typeName;
		this.attributeName = attributeName;
	}

	public boolean isEntity() {
		boolean attributeIsEntity = false;
		if (isDomainType())
			attributeIsEntity = Meva.getClassDefinition(typeName, true).entity;
		return attributeIsEntity;
	}

	public boolean isDomainType() {
		return !typeName.equals("String") && !typeName.equals("relation") && !Types.nativeTypes.contains(typeName);
	}

	public boolean isNative() {
		return Types.nativeTypes.contains(typeName);
	}

	public boolean isNativeOrString() {
		return typeName.equals("String") || Types.nativeTypes.contains(typeName);
	}

	public boolean isRelation() {
		return typeName.equals("relation");
	}

	public String getUsableType() {
		if (isRelation())
			return "RelationCollection<" + reltype + ">";
		else
			return typeName;
	}

	public String getSqlType() {
		return Types.sqlTypes.get(typeName);
	}

	public String getWrapperType() {
		return Types.wrapperTypes.get(typeName);
	}

	public ClassDefinition getClassDefinition() {
		return Meva.getClassDefinition(typeName, true);
	}

	public boolean isValue() {
		boolean attributeIsValue = false;
		if (isDomainType())
			attributeIsValue = !Meva.getClassDefinition(typeName, true).entity;
		return attributeIsValue;
	}

	public boolean isSingleRelation() {
		return isRelation() && !multipleRelation;
	}

	public boolean isMultipleRelation() {
		return isRelation() && multipleRelation;
	}

	public boolean isPrimaryTypeInMultipleRelation() {
		return containingType.compareTo(reltype) < 0;
	}

	public String getMultipleRelationTableName() {
		if(isPrimaryTypeInMultipleRelation())
			return containingType + "_" + attributeName;
		else
			return reltype + "_" + relname;
	}

	public String getOwnCoulumnName() {
		return containingType+"Which"+ClassDefinition.capitalizeFirst(attributeName)+"_id";
	}

	public String getOtherColumnName() {
		return  reltype+"Which"+ClassDefinition.capitalizeFirst(relname)+"_id";
	}
}
