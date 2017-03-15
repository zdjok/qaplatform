package com.yt.qa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yt.qa.enumbean.SessionEnum;
import com.yt.qa.util.FileUtils;

import net.sf.json.JSONObject;

/**
 * @author zhengdejing
 *
 */
@Controller
@RequestMapping("/test")
public class TestCaseDetailController {
	Logger logger = Logger.getLogger(TestCaseDetailController.class);

	@Autowired
	FileUtils fileUtils;
	
	@GetMapping("/startFunctionTest")
	public String selectFunctionTest(HttpServletRequest request, HttpServletResponse response, Model model){
		model.addAttribute(SessionEnum.USERNAME.getSessionKey(), 
				request.getSession().getAttribute(SessionEnum.USERNAME.getSessionKey()));
		return "functionTestPage";
	}
	
	@GetMapping("/getTestCaseDetail")
	@ResponseBody
	public JSONObject getTestCaseDetail(HttpServletRequest request, HttpServletResponse respnose, Model model){
		JSONObject caseJson = new JSONObject();
		HttpSession httpSession = request.getSession();
		caseJson = fileUtils.getTestCaseNumberWithFileName(httpSession.getAttribute(SessionEnum.USERNAME.getSessionKey()).toString());
		return caseJson;
	}
	
	@GetMapping(value="/interface-test")
	public String gotoInterfaceTest(HttpServletRequest request, Model model){
		HttpSession httpSession = request.getSession();
		model.addAttribute(SessionEnum.USERNAME.getSessionKey(), 
				request.getSession().getAttribute(SessionEnum.USERNAME.getSessionKey()));
		JSONObject caseJson = fileUtils.getTestCaseNumberWithFileName(httpSession.getAttribute(SessionEnum.USERNAME.getSessionKey()).toString());
		model.addAttribute("total", caseJson.get("total"));
		return "interface-test-go";
	}
}
