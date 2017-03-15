package com.yt.qa.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yt.qa.config.HosConfig;

import net.sf.json.JSONObject;

/**
 * @author zhengdejing
 */
@Component
public class TaskConsumer implements Runnable {
	Logger logger = Logger.getLogger(TaskConsumer.class);
	
	@Autowired
	Storage storage;

	@Autowired
	RequestClient client;

	@Autowired
	HosConfig hosConfig;

	boolean done;
	AtomicInteger hasToken;
	Map<TestCaseTask, JSONObject> testResultMap;

	@Override
	public void run() {
		testResultMap = new ConcurrentHashMap<TestCaseTask, JSONObject>();
		hasToken = new AtomicInteger(0);
		try {
			while (true) {
				TestCaseTask task = storage.poll();
				if (task == null) {
					logger.debug("break........");
					break;
				}
				logger.debug("消费者执行任务：" + task);
				synchronized (this) {
					JSONObject res = client.doUDBPost(
							hosConfig.getMedRestApiHost() + hosConfig.getMedPath() + task.getPath()
									.replace("{hosid}", hosConfig.getHosId()).replace("{hosId}", hosConfig.getHosId()),
							task.getInputParams());
					hasToken.incrementAndGet();
					testResultMap.put(task, res);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Map<TestCaseTask, JSONObject> getTestResultMap() {
		return testResultMap;
	}

	public void setTestResultMap(Map<TestCaseTask, JSONObject> testResultMap) {
		this.testResultMap = testResultMap;
	}

	public boolean isDone() {
		return done;
	}

	public AtomicInteger getHasToken() {
		return hasToken;
	}

	public void clear() {
		testResultMap.clear();
	}

	public void zeroHashToken() {
		hasToken.getAndSet(0);
	}
}
