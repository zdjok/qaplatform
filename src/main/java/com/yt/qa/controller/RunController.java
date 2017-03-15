package com.yt.qa.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yt.qa.core.AutomaticLogin;
import com.yt.qa.core.TaskConsumer;
import com.yt.qa.core.TestCaseTask;
import com.yt.qa.enumbean.SessionEnum;
import com.yt.qa.io.ExcelIO;
import com.yt.qa.service.ExecuteService;
import com.yt.qa.util.FileUtils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/test")
public class RunController {
	private Logger logger = Logger.getLogger(RunController.class);
	
	@Autowired
	ExecuteService execute;
	
	@Autowired
	TaskConsumer consumer;
	
	@Autowired
	FileUtils fileUtils;
	
	@Autowired
	AutomaticLogin login;
	
	@Autowired
	ExcelIO excelIO;
	
	@GetMapping("/getTaskSizeHasRunned")
	@ResponseBody
	public Map<String, Object> getTaskSizeOfToken(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String username = request.getSession().getAttribute(SessionEnum.USERNAME.getSessionKey()).toString();
		int size = execute.countTestedTask(username);
		resultMap.put("size", size);
		return resultMap;
	}
	
	@GetMapping("/getTotalTaskSize")
	@ResponseBody
	public Map<String, Object> getTotalTaskSize(HttpServletRequest request, HttpServletResponse respnose){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpSession httpSession = request.getSession();
		int size = fileUtils.getTotalTestCaseNumber(httpSession.getAttribute(SessionEnum.USERNAME.getSessionKey()).toString());
		resultMap.put("result", "OK");
		resultMap.put("size", size);
		return resultMap;
	}
	
	@PostMapping("/startInterfaceTest-before")
	@ResponseBody
	public Map<String,Object> enterInterfaceTest(HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String username = request.getSession().getAttribute(SessionEnum.USERNAME.getSessionKey()).toString();
		model.addAttribute(SessionEnum.USERNAME.getSessionKey(), username);
/*		1.创建测试报告模板
		2.登录用户接口测试权限
		3.执行测试用例
		4.写入测试结果*/
		String reportFileName = excelIO.createTestResultFile(username);
		logger.debug("report file created : " + reportFileName);
		login.doUDB2Login();
		Map<TestCaseTask, JSONObject> testResultMap = execute.execute(username);
		logger.debug("testResultMap----------" + testResultMap);
		excelIO.writeTestResult2Excel(testResultMap);
		consumer.clear();
		consumer.zeroHashToken();
		resultMap.put("result", "OK");
		resultMap.put("reportFileName", reportFileName); 
		return resultMap;
	}
	
	@PostMapping("/startInterfaceTest")
	@ResponseBody
	public Map<String,Object> startInterfaceTest(HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String username = request.getSession().getAttribute(SessionEnum.USERNAME.getSessionKey()).toString();
		model.addAttribute(SessionEnum.USERNAME.getSessionKey(), username);
/*		1.创建测试报告模板
		2.登录用户接口测试权限
		3.执行测试用例
		4.写入测试结果*/
		execute.executeTestTask(username);
		resultMap.put("result", "OK");
		return resultMap;
	}
	
	@GetMapping("/testreport/reportview")
	public String goToTestReportPage(String reportFileName, HttpServletRequest request, HttpServletResponse response, Model model){
		String username = request.getSession().getAttribute(SessionEnum.USERNAME.getSessionKey()).toString();
		model.addAttribute(SessionEnum.USERNAME.getSessionKey(), username);
		model.addAttribute("reportFileName", reportFileName);
		logger.debug(reportFileName);
		return "test-report-view";
	}
}
