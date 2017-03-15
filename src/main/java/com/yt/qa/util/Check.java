package com.yt.qa.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yt.qa.entity.UserDO;
import com.yt.qa.enumbean.ErrorMsgEnum;
import com.yt.qa.service.UserService;

/**
 * @author zhengdejing
 *
 */
@Component
public class Check {
	@Autowired
	UserService userService;
	
	@Autowired
	CipherUtils cipher;
	
	/**
	 * 登录用户校验
	 * @param user
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkUser(UserDO user, String username, String password){
		if(user == null) return false;
		if(user.getEmail() == null || user.getPassword() == null) return false;
		if(user.getEmail().equals(username) && user.getPassword().equals(cipher.encodedByMd5(username, password)))
			return true;
		else return false;
	}

	public Map<String, String> checkUserInfo(String username, String password, String password2) {
		Map<String,String> resultMap = new HashMap<String,String>();
		if(password == "" || password2 == ""){
			resultMap.put("result", "FAIL");
			resultMap.put("msg", ErrorMsgEnum.EMPTYPASSWORD.getMsg());
			return resultMap;
		}
		
		if(username == ""){
			resultMap.put("result", "FAIL");
			resultMap.put("msg", ErrorMsgEnum.EMPTYEMAIL.getMsg());
			return resultMap;
		}
		String reg = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$";
		Pattern pat = Pattern.compile(reg);
		Matcher matcher = pat.matcher(username);
		if(!matcher.find()){
			resultMap.put("result", "FAIL");
			resultMap.put("msg", ErrorMsgEnum.INVALIDEMAIL.getMsg());
			return resultMap;
		}
		
		List<UserDO> users = userService.listUsers(username);
		if(users != null && users.size() > 0){
			resultMap.put("result", "FAIL");
			resultMap.put("msg", ErrorMsgEnum.EXIST.getMsg());
			return resultMap;
		}
		return resultMap;
	}
	
	public Map<String, String> checkEmail(String email) {
		Map<String,String> resultMap = new HashMap<String,String>();
		if(email == ""){
			resultMap.put("result", "FAIL");
			resultMap.put("msg", ErrorMsgEnum.EMPTYEMAIL.getMsg());
			return resultMap;
		}
		String reg = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$";
		Pattern pat = Pattern.compile(reg);
		Matcher matcher = pat.matcher(email);
		if(!matcher.find()){
			resultMap.put("result", "FAIL");
			resultMap.put("msg", ErrorMsgEnum.INVALIDEMAIL.getMsg());
			return resultMap;
		}
		
		List<UserDO> users = userService.listUsers(email);
		if(users != null && users.size() == 0){
			resultMap.put("result", "FAIL");
			resultMap.put("msg", ErrorMsgEnum.NOTEXIST.getMsg());
			return resultMap;
		}
		return resultMap;
	}
	
}
