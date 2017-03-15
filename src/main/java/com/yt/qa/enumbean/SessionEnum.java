package com.yt.qa.enumbean;

/**
 * @author zhengdejing
 *
 */
public enum SessionEnum {
	SESSIONKEY("yuntai_autotest"),USERNAME("username");
	
	String session;
	
	private SessionEnum(String session){
		this.session = session;
	}
	
	public String getSessionKey(){
		return session;
	}
}
