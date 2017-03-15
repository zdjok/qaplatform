package com.yt.qa.entity;

import java.util.Date;

public class ReportLogDO {
	private Long id;
	private String username;
	private String currentReportPath;
	private String moduleName;
	private String apiName;
	private String caseName;
	private String expectedResult;
	private String actualResult;
	private String testResult;
	private String response;
	private int deleted;
	private Date create_time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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

	public Date getCreate_time() {
		return create_time;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

}
