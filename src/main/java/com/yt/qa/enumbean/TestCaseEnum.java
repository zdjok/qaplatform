package com.yt.qa.enumbean;

/**
 * @author zhengdejing
 *
 */
public enum TestCaseEnum {
	CASENAMETITLE("测试用例名称"),METHOD("method"),DEFAULTMOTHOD("POST"),PATH("path"),PORT("port")
	,DEFAULTPORT("80"),INPUTSIZE("入参个数"),EXPECTRESULT("Expect Result"),TESTRESULT("Test Result"),RESPONSE("Response");
	
	private String name;
	
	private TestCaseEnum(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
