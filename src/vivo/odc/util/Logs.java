package vivo.odc.util;

import org.apache.log4j.Logger;

public class Logs
{
	private static Logger	log	    = Logger.getLogger("com.gvt");
	private static Logger   logWarn	= Logger.getLogger("AppenderWarning");
	
	
	public static void error(Object message)
	{
		log.error("Máquina:"+InetAddressUtil.getHostName()+"##"+message);
	}

	public static void error(Object message, Throwable t)
	{
		log.error("Máquina:"+InetAddressUtil.getHostName()+"##"+message, t);
	}
	
	public static void debug(Object message)
	{
		log.debug(message);
	}
	 
	public static void info(Object message) 
	{
		log.info(message);
	}
	
	public static void warn(Object message)
	{
		logWarn.error("Máquina:"+InetAddressUtil.getHostName()+"##"+message);
	}
	
	public static void warn(Object message,Throwable t)
	{
		logWarn.error("Máquina:"+InetAddressUtil.getHostName()+"##"+message, t);
	}
	
}
