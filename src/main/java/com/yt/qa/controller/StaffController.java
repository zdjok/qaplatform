package com.yt.qa.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yt.qa.entity.StaffDO;
import com.yt.qa.service.StaffService;

/**
 * @author zhengdejing
 * 没用
 *
 */
@RestController
@RequestMapping("/staff")
public class StaffController {
	
	@Resource
	StaffService staffService;
	
	@GetMapping("/{id}")
	public StaffDO showStaff(@PathVariable("id") int id){
		return staffService.selectStaffById(id);
	}
}
