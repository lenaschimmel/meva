package de.gmino.meva.shared;

import java.util.Map;
import java.util.TreeMap;

public class EntityTypeName {
	private final String typeName;
	private static Map<String, EntityTypeName> types = new TreeMap<String, EntityTypeName>();

	private EntityTypeName(String typeName)
	{
		this.typeName = typeName;
	}
	
	@Override
	public String toString() {
		return typeName;
	}
	
	public static EntityTypeName getByString(String typeName) {
		EntityTypeName ret = types.get(typeName);
		if(ret == null)
		{
			ret = new EntityTypeName(typeName);
			types.put(typeName, ret);
		}
		return ret;
	}
}
