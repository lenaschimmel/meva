package de.gmino.meva.shared;

public class Util {
	private static UtilImpl impl;
	
	public static void setImpl(UtilImpl impl) {
		Util.impl = impl;
	}

	public static String format(String formatString, Object... arguments)
	{
		if(impl == null)
			throw new RuntimeException("You must first pimpl the impl!");
		return impl.format(formatString, arguments);
	}
	
	public static String getBaseUrl()
	{
		if(impl == null)
			throw new RuntimeException("You must first pimpl the impl!");
		return impl.getBaseUrl();
	}
}
