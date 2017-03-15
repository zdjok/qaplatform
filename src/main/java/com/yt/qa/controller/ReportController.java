package com.yt.qa.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yt.qa.core.ReportVO;
import com.yt.qa.enumbean.SessionEnum;
import com.yt.qa.service.ReportService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("test")
public class ReportController {
	private static Logger logger = Logger.getLogger(ReportController.class);
	
	@Autowired
	ReportService reportService;
	
	@PostMapping("/report/getCurrentReportDetail")
	@ResponseBody
	public Map<String,Object> getCurrentReportDetail(HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		HttpSession httpSession = request.getSession();
		String username = httpSession.getAttribute(SessionEnum.USERNAME.getSessionKey()).toString();
		List<ReportVO> reportList = reportService.getCurrentReportDetail(username);
		JSONArray arr = JSONArray.fromObject(reportList);
		resultMap.put("result", "OK");
		resultMap.put("reportItem", arr);
		return resultMap;
	}
	
	@PostMapping("/report/getReportInfo")
	@ResponseBody
	public Map<String,Object> getReportInfo(HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		HttpSession httpSession = request.getSession();
		String username = httpSession.getAttribute(SessionEnum.USERNAME.getSessionKey()).toString();
		resultMap = reportService.getReportInfo(username);
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>" + resultMap);
		
		return resultMap;
	}
	
	@GetMapping("/report/downloadReport")
	public ResponseEntity<InputStreamResource> downloadReport(String reportfile, HttpServletRequest request
			, HttpServletResponse respnose) throws IOException{
		logger.debug("下载：" + reportfile);
        FileSystemResource file = new FileSystemResource(reportfile);  
        HttpHeaders headers = new HttpHeaders();  
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));  
        headers.add("Pragma", "no-cache");  
        headers.add("Expires", "0");  
  
        return ResponseEntity  
                .ok()  
                .headers(headers)  
                .contentLength(file.contentLength())  
                .contentType(MediaType.parseMediaType("application/octet-stream"))  
                .body(new InputStreamResource(file.getInputStream()));  
	}
}
