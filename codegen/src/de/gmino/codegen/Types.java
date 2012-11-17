package de.gmino.codegen;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Types {
	public static Set<String> nativeTypes = new TreeSet<String>();
	public static Map<String, String> sqlTypes = new TreeMap<String, String>();
	public static Map<String, String> wrapperTypes = new TreeMap<String, String>();

	static void registerNativeType(String nativeType, String sqlType, String wrapperType) {
		nativeTypes.add(nativeType);
		sqlTypes.put(nativeType, sqlType);
		wrapperTypes.put(nativeType, wrapperType);
	}

	static {
		registerNativeType("int", "INT", "Integer");
		registerNativeType("byte", "TINYINT", "Byte");
		registerNativeType("boolean", "BOOLEAN", "Boolean");
		registerNativeType("char", "SMALLINT", "Character");
		registerNativeType("double", "DOUBLE", "Double");
		registerNativeType("float", "FLOAT", "Float");
		registerNativeType("long", "BIGINT", "Long");
		registerNativeType("short", "SMALLINT", "Short");
	}
}
