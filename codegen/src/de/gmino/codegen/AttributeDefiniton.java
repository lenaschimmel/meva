package de.gmino.codegen;

public class AttributeDefiniton {
	String attributeName;
	String typeName;
	public int maxLen = 1000;
	
	public AttributeDefiniton(String typeName, String attributeName) {
		super();
		this.typeName = typeName;
		this.attributeName = attributeName;
	}
	
}
