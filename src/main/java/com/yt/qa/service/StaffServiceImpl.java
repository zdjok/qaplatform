package com.yt.qa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yt.qa.dao.StaffMapper;
import com.yt.qa.entity.StaffDO;

/**
 * @author zhengdejing
 *
 */
@Service
public class StaffServiceImpl implements StaffService {
	
	@Autowired
	StaffMapper staffMapper;
	
	@Override
	public StaffDO selectStaffById(int id) {
		// TODO Auto-generated method stub
		return staffMapper.selectStaffById(id);
	}

	@Override
	public List<StaffDO> getAllStaff() {
		// TODO Auto-generated method stub
		return staffMapper.getAllStaff();
	}

}
