package de.gmino.checkin.client;

import com.google.gwt.core.client.JsArrayString;

import de.gmino.meva.shared.UtilImpl;

public class UtilClient implements UtilImpl {
	public String getBaseUrl()
	{
		return "http://192.168.178.25:8888/";
	}
	
	public String format(String formatString, Object... arguments)
	{
		JsArrayString array = (JsArrayString)JsArrayString.createArray();

		for (Object o : arguments) {
			array.push(o.toString());
		}

		return nativeFormat(formatString, array);
	}

	private static native String nativeFormat(String formatString, JsArrayString arguments)  /*-{
	  // arguments is a normal JavaScript array of strings
	  return $wnd.vsprintf(formatString, arguments);
	}-*/;
	

}
