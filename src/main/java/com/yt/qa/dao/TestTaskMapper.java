package com.yt.qa.dao;

import org.apache.ibatis.annotations.Param;

import com.yt.qa.core.TestCaseTask;

public interface TestTaskMapper {
	public int insertTestTaskLog(TestCaseTask task);

	public int countTestedTask(@Param("userName") String username);

	public void updateTestTaskByUserName(@Param("userName") String username);
}
