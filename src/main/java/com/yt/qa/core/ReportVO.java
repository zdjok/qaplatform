package com.yt.qa.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ReportVO {
	private Map<String, String> pathNameMap = new HashMap<String, String>();
	private String currentReportPath;

	private String moduleName;
	private String apiName;
	private String caseName;
	private String expectedResult;
	private String actualResult;
	private String testResult;
	private String response;

	public String getCurrentReportPath() {
		return currentReportPath;
	}

	public void setCurrentReportPath(String currentReportPath) {
		this.currentReportPath = currentReportPath;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getActualResult() {
		return actualResult;
	}

	public void setActualResult(String actualResult) {
		this.actualResult = actualResult;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Map<String, String> getPathNameMap() {
		return pathNameMap;
	}

}
