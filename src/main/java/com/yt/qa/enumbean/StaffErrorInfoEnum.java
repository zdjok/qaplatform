package com.yt.qa.enumbean;

import com.yt.qa.errorhandler.ErrorInfoInterface;

public enum StaffErrorInfoEnum implements ErrorInfoInterface {
	PARAM_NO_COMPLETE("000001","params no complete"),
	STAFF_ID_INVALID("000002","staff id not exist");
	
	private String code;
	private String message;
	
	private StaffErrorInfoEnum(String code, String message){
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
}
