package com.yt.qa.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

/**
 * @author zhengdejing
 *
 */
@Component
public class Storage {
	BlockingQueue<TestCaseTask> queue = new LinkedBlockingQueue<TestCaseTask>();
	private int totalTaskSize;
	private int currentTaskSize;
	
	public void offer(TestCaseTask testCase) throws InterruptedException{
		queue.offer(testCase, 5, TimeUnit.SECONDS);
		totalTaskSize += 1;
		currentTaskSize += 1;
	}
	
	public TestCaseTask poll() throws InterruptedException{
		totalTaskSize -= 1;
		currentTaskSize -= 1;
		return queue.poll(5, TimeUnit.SECONDS);
	}
	
	public int getTotalTaskSize() {
		return totalTaskSize;
	}

	public void setTotalTaskSize(int totalTaskSize) {
		this.totalTaskSize = totalTaskSize;
	}

	public int getCurrentTaskSize() {
		return currentTaskSize;
	}

	public void setCurrentTaskSize(int currentTaskSize) {
		this.currentTaskSize = currentTaskSize;
	}

	public BlockingQueue<TestCaseTask> getQueue() {
		return queue;
	}
	
}
