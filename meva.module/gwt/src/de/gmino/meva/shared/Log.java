package de.gmino.meva.shared;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
	private static Logger logger;
	
	public static void log(String msg)
	{
		getLogger().info(msg);
	}
	
	public static void exception(String msg, Throwable e) {
		getLogger().log(Level.SEVERE, msg, e);
	}
	
	public static Logger getLogger()
	{
		if(logger == null)
		{
			logger = Logger.getLogger("Global logger");
			logger.setLevel(Level.ALL);
		}
		return logger;
	}
}
