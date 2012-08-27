package de.gmino.meva.android.request;

import de.gmino.meva.shared.UtilImpl;

public class UtilAndroid implements UtilImpl{
	public String getBaseUrl()
	{
//		return "http://192.168.178.64:8888/";
		return "http://134.169.137.217:8888/";
	}
	
	public String format(String formatString, Object... arguments)
	{
		return String.format(formatString, arguments);
	}
}
