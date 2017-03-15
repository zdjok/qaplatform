package com.yt.qa.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yt.qa.core.TaskConsumer;
import com.yt.qa.core.TaskProducer;
import com.yt.qa.core.TaskProducerSchedule;
import com.yt.qa.core.TestCaseTask;
import com.yt.qa.dao.TestTaskMapper;

import net.sf.json.JSONObject;

/**
 * @author zhengdejing
 *
 */
@Service
public class ExecuteServiceImpl implements ExecuteService {
	Logger logger = Logger.getLogger(ExecuteServiceImpl.class);
	
	@Autowired
	TaskConsumer taskConsumer;
	
	@Autowired
	TaskProducer taskProducer;
	
	@Autowired
	TaskProducerSchedule taskProducerSchedule;
	
	@Autowired
	TestTaskMapper reportMapper;
	
	Map<TestCaseTask, JSONObject> testResultMap;

	@Override
	public Map<TestCaseTask, JSONObject> execute(String username) {
		//1. 启动读取测试用例线程并放入仓库
		//2. 启动执行测试用例线程从仓库获取并执行，然后将测试结果放入一个Map
		
		taskProducer.setUsername(username);
		Thread producerThread = new Thread(taskProducer, username);
		producerThread.start();
		
		Thread consumerThread1 = new Thread(taskConsumer);
		Thread consumerThread2 = new Thread(taskConsumer);
		
		consumerThread1.start();
		consumerThread2.start();
		try {
			consumerThread1.join();
			consumerThread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		testResultMap = taskConsumer.getTestResultMap();
		
		return testResultMap;
	}
	
	public void executeTestTask(String username){
		taskProducerSchedule.addTask(username);
	}
	
	public int insertReportLog(TestCaseTask task){
		return reportMapper.insertTestTaskLog(task);
	}
	
	public int countTestedTask(String username){
		return reportMapper.countTestedTask(username);
	}
	
	public void updateTestTaskByUserName(String username){
		reportMapper.updateTestTaskByUserName(username);
	}
}
