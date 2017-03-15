package com.yt.qa.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yt.qa.entity.UserDO;
import com.yt.qa.enumbean.PasswordEnum;
import com.yt.qa.enumbean.SessionEnum;
import com.yt.qa.service.UserService;
import com.yt.qa.util.Check;
import com.yt.qa.util.CipherUtils;

/**
 * @author zhengdejing
 *
 */
@Controller
public class LoginController {
	
	Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	Check check;
	
	@Autowired
	CipherUtils cipher;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String startPage(){
		return "login";
	}
	
	//session检查跳转可以放在拦截器
	@GetMapping("/login")
	public String showLoginPage(){
		return "login";
	}
	
	//添加@ResponseBody，告诉后台以返回类型数据返回，不添加的话会去查找对应的html模板
	@PostMapping("/doLogin")
	@ResponseBody
	public Map<String, Object> loginPost(String username, String password, HttpServletRequest req, HttpServletResponse res){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserDO loginUser = userService.getUser(username, cipher.encodedByMd5(username, password));
		boolean success = check.checkUser(loginUser, username, password);
		if(!success){
			resultMap.put("result", "Username or password is not correct.");
			return resultMap;
		}
		HttpSession session = req.getSession();
		UUID uid = (UUID) session.getAttribute(SessionEnum.SESSIONKEY.getSessionKey());
		if(uid == null){
			uid = UUID.randomUUID();
			logger.debug("session uid : " + uid);
		}
		session.setAttribute(SessionEnum.SESSIONKEY.getSessionKey(), uid);
		session.setAttribute(SessionEnum.USERNAME.getSessionKey(), username);
		resultMap.put("result", "OK");
		return resultMap;
	}
	
	@RequestMapping(value="/doLoginOut", method={RequestMethod.GET,RequestMethod.POST})
	public String loginOut(HttpServletRequest req, HttpServletResponse res){
		HttpSession httpSession = req.getSession();
		httpSession.removeAttribute(SessionEnum.SESSIONKEY.getSessionKey());
		httpSession.removeAttribute(SessionEnum.USERNAME.getSessionKey());
		httpSession.invalidate();
		return "login";
	}
	
	@PostMapping("/resetPassword")
	@ResponseBody
	public Map<String,String> resetPassword(String email){
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap = check.checkEmail(email);
		if(resultMap != null && resultMap.size() > 0){
			return resultMap;
		}else{
			userService.updatePassword(email, cipher.encodedByMd5(email, PasswordEnum.DEFAULTPASSWORD.getPassword()));
		}
		resultMap.put("result", "OK");
		return resultMap;
	}
}
