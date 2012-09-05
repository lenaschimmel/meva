package de.gmino.checkin.android.facebook.errorhandling;

import org.itemscript.core.values.JsonObject;

import de.gmino.checkin.android.facebook.FacebookUtil;

public class FacebookErrorListener {
	Throwable constructionContext;
	public FacebookErrorListener() {
		constructionContext = new Throwable();
	}
	
	public void handleError(Throwable innerCause)
	{
		System.err.println("Error while communicatin with Facebook. Inner error:");
		innerCause.printStackTrace();
		System.err.println("Asynchronous call was made from:");
		constructionContext.printStackTrace();
		FacebookUtil.handleLoggedOutCondition();
	}
	
	public void handleError(Throwable innerCause, Object state)
	{
		System.err.println("Error while communicatin with Facebook. Inner error:");
		innerCause.printStackTrace();
		System.err.println("State Object:");
		System.err.println(state.toString());
		System.err.println("Asynchronous call was made from:");
		constructionContext.printStackTrace();
		FacebookUtil.handleLoggedOutCondition();
	}
	
	protected boolean checkResponse(JsonObject response)
	{
		if(response.containsKey("error"))
		{
			System.err.println("Error in response: " + response.toCompactJsonString());
			int errorCode = response.getObject("error").getInt("code");
			if(errorCode == 190)
				FacebookUtil.handleLoggedOutCondition();
			return false;
		}
		return true;
	}
}
