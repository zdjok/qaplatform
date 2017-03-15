package com.yt.qa.enumbean;

/**
 * @author zhengdejing
 *
 */
public enum PasswordEnum {
	DEFAULTPASSWORD("123456");
	
	private String password;
	
	private PasswordEnum(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return password;
	}
}
