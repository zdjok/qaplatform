package com.yt.qa.service;

import java.util.List;

import com.yt.qa.entity.StaffDO;

/**
 * @author zhengdejing
 *
 */
public interface StaffService {
	StaffDO selectStaffById(int id);
	
	List<StaffDO> getAllStaff();
}
