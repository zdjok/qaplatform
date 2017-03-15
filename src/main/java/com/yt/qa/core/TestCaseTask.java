package com.yt.qa.core;

import net.sf.json.JSONObject;

/**
 * @author zhengdejing
 *
 */
public class TestCaseTask {
	private String userName;
	private String excelFile;
	private String sheetName;
	private String caseName;
	private String method;
	private String path;
	private String port;
	private int inputNum;
	private JSONObject inputParams;
	private String expectResult;
	private String testResult;
	private String Response;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(String excelFile) {
		this.excelFile = excelFile;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public int getInputNum() {
		return inputNum;
	}

	public void setInputNum(int inputNum) {
		this.inputNum = inputNum;
	}

	public JSONObject getInputParams() {
		return inputParams;
	}

	public void setInputParams(JSONObject inputParams) {
		this.inputParams = inputParams;
	}

	public String getExpectResult() {
		return expectResult;
	}

	public void setExpectResult(String expectResult) {
		this.expectResult = expectResult;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getResponse() {
		return Response;
	}

	public void setResponse(String response) {
		Response = response;
	}

	@Override
	public String toString() {
		return "TestCaseTask [userName=" + userName + ", excelFile=" + excelFile + ", sheetName=" + sheetName
				+ ", caseName=" + caseName + ", method=" + method + ", path=" + path + ", port=" + port + ", inputNum="
				+ inputNum + ", inputParams=" + inputParams + ", expectResult=" + expectResult + ", testResult="
				+ testResult + ", Response=" + Response + "]";
	}

}
