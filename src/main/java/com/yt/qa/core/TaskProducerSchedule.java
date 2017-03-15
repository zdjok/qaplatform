package com.yt.qa.core;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yt.qa.io.ExcelIO;
import com.yt.qa.util.FileUtils;

/**
 * @author zhengdejing
 *
 */
@Component
public class TaskProducerSchedule {
	Logger logger = Logger.getLogger(TaskProducerSchedule.class);
	
	@Autowired
	TaskStorage storage;
	
	@Autowired
	FileUtils fileUtils;
	
	@Autowired
	ExcelIO excelIO;
	
	public void addTask(String username){
		File dir = fileUtils.getUserSavedDirOfTestCase(username);
		List<String> fileList = fileUtils.listFileNamesByDir(dir);
		List<TestCaseTask> taskList = excelIO.getTestCaseTask(dir, fileList, username);
		logger.debug("任务个数：{}" + taskList.size());
		try {
			storage.offer(taskList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
