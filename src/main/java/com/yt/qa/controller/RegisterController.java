package com.yt.qa.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yt.qa.service.UserService;
import com.yt.qa.util.Check;
import com.yt.qa.util.CipherUtils;

/**
 * @author zhengdejing
 *
 */
@Controller
public class RegisterController {
	Logger logger = Logger.getLogger(RegisterController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	Check check;
	
	@Autowired
	CipherUtils cipher;
	
	@RequestMapping("/register")
	@ResponseBody
	public Map<String,String> register(String email, String password, String password2){
		System.out.println("进入后台注册流程。");
		Map<String,String> resultMap = new HashMap<String,String>();
		
		resultMap = check.checkUserInfo(email, password, password2);
		if(resultMap.size() > 0){
			return resultMap;
		}else{
			System.out.println("后台检测通过，开始插入数据库。");
			userService.addUser(email.substring(0, email.indexOf("@")), cipher.encodedByMd5(email, password), email);
		}
		
		resultMap.put("result", "OK");
		return resultMap;
	}
}
