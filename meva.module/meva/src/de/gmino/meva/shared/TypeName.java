package de.gmino.meva.shared;

import java.util.Map;
import java.util.TreeMap;

public class TypeName implements Comparable<TypeName> {
	private final String typeName;
	private static Map<String, TypeName> types = new TreeMap<String, TypeName>();
	private static Object dummy = new Object();
	private boolean isNative;
	private boolean isValue;
	private boolean isEntity;
	
	public static final TypeName Boolean = getNativeByString("Boolean", true);
	public static final TypeName String = getNativeByString("String", true);
	public static final TypeName Integer = getNativeByString("Integer", true);
	public static final TypeName Double = getNativeByString("Double", true);
	public static final TypeName Float = getNativeByString("Float", true);
	public static final TypeName Long = getNativeByString("Long", true);

	static
	{
		System.out.println("INITIALIZED TYPENAME " + dummy.hashCode());
	}
	
	private TypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String toString() {
		return typeName;
	}

	public static TypeName getNativeByString(String typeName, boolean createNew) {
		final TypeName ret = getInternalByString(typeName, createNew);
		if(createNew)
		{
			System.out.println("Registered native TypeName " + typeName + " in " + dummy.hashCode());
			ret.isNative = true;
		}
		else if (!ret.isNative)
			throw new RuntimeException("Type " + typeName + " is not native.");
		return  ret;
	}

	public static TypeName getEntityByString(String typeName, boolean createNew) {
	
		final TypeName ret = getInternalByString(typeName, createNew);
		if(createNew)
		{
			System.out.println("Registered entity TypeName " + typeName + " in " + dummy.hashCode());
			ret.isEntity = true;
		}
		else if (!ret.isEntity)
			throw new RuntimeException("Type " + typeName + " is not an entity.");
		return  ret;
	}

	public static TypeName getValueByString(String typeName, boolean createNew) {
		final TypeName ret = getInternalByString(typeName, createNew);
		if(createNew)
		{
			System.out.println("Registered value TypeName " + typeName + " in " + dummy.hashCode());
			ret.isValue = true;
		}
		else if (!ret.isValue)
			throw new RuntimeException("Type " + typeName + " is not a value.");
		
		return  ret;
	}


	public static TypeName getByString(String typeName) {
		final TypeName ret = getInternalByString(typeName, false);
		if (ret == null)
			throw new RuntimeException("Did not find type " + typeName + " in " + dummy.hashCode());
		return  ret;
	}

	private static TypeName getInternalByString(String typeName, boolean createNew) {
		TypeName ret = types.get(typeName);
		if (ret == null) {
			if(createNew)
			{
				ret = new TypeName(typeName);
				types.put(typeName, ret);
			}
			else
			{
				for(String key : types.keySet())
					System.err.println("Known type name: " + key);
				throw new RuntimeException("Did not find type " + typeName + " in " + dummy.hashCode());
			}
		}
		return ret;
	}

	@Override
	public int compareTo(TypeName that) {
		return this.typeName.compareTo(that.typeName);
	}

	public boolean isNative() {
		return isNative;
	}

	public boolean isValue() {
		return isValue;
	}

	public boolean isEntity() {
		return isEntity;
	}
}
