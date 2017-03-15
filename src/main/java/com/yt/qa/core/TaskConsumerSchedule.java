package com.yt.qa.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yt.qa.config.HosConfig;
import com.yt.qa.io.ExcelIO;
import com.yt.qa.service.ExecuteService;

import net.sf.json.JSONObject;

/**
 * @author zhengdejing
 *
 */
@Component
public class TaskConsumerSchedule {
	Logger logger = Logger.getLogger(TaskConsumerSchedule.class);
	
	@Autowired
	TaskStorage storage;
	
	@Autowired
	RequestClient client;

	@Autowired
	HosConfig hosConfig;
	
	@Autowired
	AutomaticLogin login;
	
	@Autowired
	ExcelIO excelIO;
	
	@Autowired
	ExecuteService executeServie;

	@Scheduled(cron="*/1 * * * * ?")
	public void cosumerTask(){
		try {
			List<TestCaseTask> taskList = storage.poll();
			if(taskList != null && taskList.size() > 0){
				login.doUDB2Login();
				Map<TestCaseTask, JSONObject> testResultMap = new HashMap<TestCaseTask, JSONObject>();
				String username = taskList.get(0).getUserName();
				//创建测试报告文件
				excelIO.createTestResultFile(username);
				//修改数据库对应用户名并且delete=0的测试记录
				executeServie.updateTestTaskByUserName(username);
				//执行本次提交的测试用例
				for(TestCaseTask task : taskList){
					JSONObject res = client.doUDBPost(
							hosConfig.getMedRestApiHost() + hosConfig.getMedPath() + task.getPath()
									.replace("{hosid}", hosConfig.getHosId()).replace("{hosId}", hosConfig.getHosId()),
							task.getInputParams());
					testResultMap.put(task, res);
					//插入数据库测试记录
					executeServie.insertReportLog(task);
				}
				//测试结果写入文件
				excelIO.writeTestResult2Excel(testResultMap);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
