package com.yt.qa.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yt.qa.entity.StaffDO;
import com.yt.qa.enumbean.StaffErrorInfoEnum;
import com.yt.qa.errorhandler.GlobalErrorInfoException;
import com.yt.qa.service.StaffService;

/**
 * @author zhengdejing
 * 没用
 *
 */
@RestController
@RequestMapping("/staff")
public class StaffController {
	Logger logger = Logger.getLogger(StaffController.class);
	
	@Resource
	StaffService staffService;
	
	@GetMapping("/{id}")
	public StaffDO showStaff(@PathVariable("id") int id) throws GlobalErrorInfoException{
		StaffDO staff = staffService.selectStaffById(id);
		if(staff == null)
			throw new GlobalErrorInfoException(StaffErrorInfoEnum.STAFF_ID_INVALID);
		return staff;
	}
}
