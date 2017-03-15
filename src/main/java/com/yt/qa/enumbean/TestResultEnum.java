package com.yt.qa.enumbean;

/**
 * @author zhengdejing
 *
 */
public enum TestResultEnum {
	MODULE(0, "模块名称"),INTERFACENAME(1,"接口"),TESTCASENAME(2,"测试用例名称"),EXPECTRESULT(3,"预期结果"),ACTUALRESULT(4,"实际结果"),
	TESTRESULT(5, "测试结果"),RESPONSE(6,"返回结果"),PASS(7,"PASS"),FAIL(8,"FAIL"),SHEETNAME(9, "TestResult");
	
	private int index;
	private String title;
	
	private TestResultEnum(int index, String title){
		this.index = index;
		this.title = title;
	}
	
	public static String getTitle(int index){
		for(TestResultEnum t : TestResultEnum.values()){
			if(index == t.getIndex()){
				return t.getTitle();
			}
		}
		return null;
	}
	
	public int getIndex(){
		return index;
	}
	
	public String getTitle(){
		return title;
	}
}
