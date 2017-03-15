package com.yt.qa.util;

/**
 * @author zhengdejing
 *
 */
public class EnvUtils {
	
	public static boolean isLinux(){
		boolean isLinux = false;
		if(!(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1)){
			isLinux = true;
		}
		return isLinux;
	}
}
