package vivo.odc.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author G0011023
 * */
public class InetAddressUtil {
	
	/**
	 * Obtem o hostname do servidor
	 * */
	public static String getHostName(){
		String host = "HOST_UNKNOW";
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return host;
	}
}
