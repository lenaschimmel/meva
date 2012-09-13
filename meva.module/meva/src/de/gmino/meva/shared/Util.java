package de.gmino.meva.shared;

public class Util {
	private static UtilImpl impl;

	public static void setImpl(UtilImpl impl) {
		Util.impl = impl;
	}

	public static String format(String formatString, Object... arguments) {
		if (impl == null)
			throw new RuntimeException("You must first pimpl the impl!");
		return impl.format(formatString, arguments);
	}

	public static String getBaseUrl() {
		if (impl == null)
			throw new RuntimeException("You must first pimpl the impl!");
		return impl.getBaseUrl();
	}

	public  static <T> T randomElementFrom(T[] array) {
		return array[(int) (0.999 * Math.random() * array.length)];
	}
	
	public static String capitalizeFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	public static String escapeForJson(String unescaped)
	{
		return unescaped.replace("\\","\\\\").replace("\"","\\\"");
	}
}
