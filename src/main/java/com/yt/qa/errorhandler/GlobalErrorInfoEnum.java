package com.yt.qa.errorhandler;

public enum GlobalErrorInfoEnum implements ErrorInfoInterface{
	SUCCESS("0","success"), NOT_FOUND("-1","service not found");

	private String code;
	private String message;
	
	private GlobalErrorInfoEnum(String code, String message){
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
