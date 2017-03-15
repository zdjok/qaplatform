package com.yt.qa.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yt.qa.enumbean.SessionEnum;

/**
 * @author zhengdejing
 *
 */
@Controller
@RequestMapping("/test")
public class SelectTypeController {
	
	Logger logger = Logger.getLogger(SelectTypeController.class);

	@GetMapping("/select-type")
	public String selectModule(HttpServletRequest request, HttpServletResponse response, Model model){
		model.addAttribute("username", request.getSession().getAttribute(SessionEnum.USERNAME.getSessionKey()));
		return "select-type";
	}
	
	
	@PostMapping("/selectInterfaceTest")
	@ResponseBody
	public HashMap<String,String> selectInterfaceTest(){
		HashMap<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("result", "OK");
		return resultMap;
	}
	
	@PostMapping("/functionTest")
	public String selectFunctionTest(){
		return "functionTestPage";
	}
	
	@GetMapping("/interface-test-prepare")
	public String enterInterfaceTest(HttpServletRequest request, HttpServletResponse response, Model model){
		model.addAttribute(SessionEnum.USERNAME.getSessionKey(), 
				request.getSession().getAttribute(SessionEnum.USERNAME.getSessionKey()));
		return "interface-test-prepare-page";
	}
}
