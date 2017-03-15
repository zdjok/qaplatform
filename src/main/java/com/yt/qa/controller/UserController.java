package com.yt.qa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yt.qa.entity.UserDO;
import com.yt.qa.service.UserService;

/**
 * @author zhengdejing
 *  ---->没用
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService service;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public UserDO view(@PathVariable("username") String username, @PathVariable("password") String password){
		return service.getUser(username, password);
	}
}
