package com.yt.qa.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yt.qa.enumbean.SessionEnum;
import com.yt.qa.util.FileUtils;

/**
 * @author zhengdejing
 *
 */
@Controller
public class UploadController {
	Logger logger = Logger.getLogger(UploadController.class);
	
	@Autowired
	FileUtils fileUtils;
	
	@PostMapping("/upload")
	@ResponseBody
	public Map<String,String> upload(HttpServletRequest request, HttpServletResponse respnose, @RequestParam("file") MultipartFile file){
		Map<String,String> resultMap = new HashMap<String,String>();
		InputStream in = null;
		OutputStream out = null;
		if("".equals(file.getOriginalFilename())){
			resultMap.put("NOTFOUND", "YES");
			return resultMap;
		}
		try {
			HttpSession httpSession = request.getSession();
			File savedDir = fileUtils.getUserSavedDirOfTestCase(httpSession.getAttribute(SessionEnum.USERNAME.getSessionKey()).toString());
			if(!savedDir.exists()){
				savedDir.mkdirs();
			}
			boolean isExist = fileUtils.fileIsExist(savedDir, file.getOriginalFilename());
			if(isExist){
				resultMap.put("exist", "YES");
			}else{
				resultMap.put("exist", "NO");
			}
			File savedFile = new File(savedDir, file.getOriginalFilename());
			in = file.getInputStream();
			out = new FileOutputStream(savedFile);
			byte[] bytes = new byte[1024];
			int len = 0;
			while((len = in.read(bytes, 0, bytes.length)) != -1){
				out.write(bytes, 0, len);
			}
			resultMap.put("result", "OK");
			resultMap.put("filename", file.getOriginalFilename());
		} catch (IOException e) {
			e.printStackTrace();
			resultMap.put("result", "ERROR");
			return resultMap;
		} finally{
			try {
				if(null != in)
					in.close();
				if(null != out)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}
	
	@GetMapping("/getUploadedFiles")
	@ResponseBody
	public Map<String,Object> getUploadedFiles(HttpServletRequest request, HttpServletResponse respnose){
		System.out.println(">>>>>>>>>>>>>>>>>>>进入获取已有文件方法。");
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<String> nameList = new ArrayList<String>();
		try {
			HttpSession httpSession = request.getSession();
			File savedDir = fileUtils.getUserSavedDirOfTestCase(httpSession.getAttribute(SessionEnum.USERNAME.getSessionKey()).toString());
			if(!savedDir.exists()){
				savedDir.mkdirs();
			}
			nameList = fileUtils.listFileNamesByDir(savedDir);
			resultMap.put("result", "OK");
			resultMap.put("filelist", nameList);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "ERROR");
			return resultMap;
		}
		return resultMap;
	}
	
	@PostMapping("/deleteExcelFile")
	@ResponseBody
	public Map<String,Object> deleteExcelFile(HttpServletRequest request, HttpServletResponse respnose, String excelFileName){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		HttpSession httpSession = request.getSession();
		File savedDir = fileUtils.getUserSavedDirOfTestCase(httpSession.getAttribute(SessionEnum.USERNAME.getSessionKey()).toString());
		File delFile = new File(savedDir, excelFileName);
		try{
			if(delFile.exists()){
				delFile.delete();
				resultMap.put("result", "OK");
			}
		} catch(Exception e){
			resultMap.put("result", "FAIL");
		}
		
		return resultMap;
	}
	
	@GetMapping("/downloadTemplate")
    public ResponseEntity<InputStreamResource> downloadFile(HttpServletRequest request, HttpServletResponse respnose)  
            throws IOException {
        String filePath = System.getProperty("user.dir") + "/testcase/TestCase-Template.xlsx";
        FileSystemResource file = new FileSystemResource(filePath);  
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
	
	@GetMapping("/test/interface-test-detail-page")
	public String uploadPage(HttpServletRequest request, HttpServletResponse response, Model model){
		model.addAttribute("username", request.getSession().getAttribute(SessionEnum.USERNAME.getSessionKey()));
		return "interface-test-detail";
	}
	
}
