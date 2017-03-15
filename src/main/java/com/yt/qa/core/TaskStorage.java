package com.yt.qa.core;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

/**
 * @author zhengdejing
 *
 */
@Component
public class TaskStorage {
	BlockingQueue<List<TestCaseTask>> queue = new LinkedBlockingQueue<List<TestCaseTask>>();
	
	public void offer(List<TestCaseTask> testCaseTaskList) throws InterruptedException{
		queue.offer(testCaseTaskList);
	}
	
	public List<TestCaseTask> poll() throws InterruptedException{
		return queue.poll();
	}
	
}
