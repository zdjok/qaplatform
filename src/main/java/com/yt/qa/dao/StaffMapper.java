package com.yt.qa.dao;

import java.util.List;

import com.yt.qa.entity.StaffDO;

/**
 * @author zhengdejing
 *
 */
public interface StaffMapper {
	StaffDO selectStaffById(int id);
	
	List<StaffDO> getAllStaff();
}
