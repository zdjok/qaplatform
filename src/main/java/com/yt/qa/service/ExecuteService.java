package com.yt.qa.service;

import java.util.Map;

import com.yt.qa.core.TestCaseTask;

import net.sf.json.JSONObject;

/**
 * @author zhengdejing
 *
 */
public interface ExecuteService {
	
	public Map<TestCaseTask, JSONObject> execute(String username);
	
	public void executeTestTask(String username);
	
	public int insertReportLog(TestCaseTask task);
	
	public int countTestedTask(String username);
	
	public void updateTestTaskByUserName(String username);
}
