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
public class TaskProducer implements Runnable{
	Logger logger = Logger.getLogger(TaskProducer.class);
	
	private String username;
	
	@Autowired
	FileUtils fileUtils;
	
	@Autowired
	ExcelIO excelIO;
	
	@Autowired
	Storage storage;
	
/*	public TaskProducer(String username, Storage storage) {
		this.username = username;
		this.storage = storage;
	}*/
	
	@Override
	public void run(){
		File dir = fileUtils.getUserSavedDirOfTestCase(username);
		List<String> fileList = fileUtils.listFileNamesByDir(dir);
		List<TestCaseTask> taskList = excelIO.getTestCaseTask(dir, fileList, username);
		logger.debug("任务个数：" + taskList.size());
		for(TestCaseTask t : taskList){
			try {
				storage.offer(t);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
