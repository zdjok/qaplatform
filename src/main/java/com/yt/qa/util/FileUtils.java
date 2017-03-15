package com.yt.qa.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yt.qa.core.ReportVO;
import com.yt.qa.enumbean.ReportEnum;
import com.yt.qa.io.ExcelIO;

import net.sf.json.JSONObject;

/**
 * @author zhengdejing
 *
 */
@Component
public class FileUtils {
	
	@Autowired
	ExcelIO excelIO;
	
	@Autowired
	ReportVO reportVO;
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	public boolean fileIsExist(File dir, String filename) throws FileNotFoundException{
		if(filename == "" || filename == null) throw new IllegalArgumentException("文件名不能为空！");
		if(dir == null) throw new FileNotFoundException("文件夹不存在！");
		File[] files = dir.listFiles();
		for(File f : files){
			if(filename.equals(f.getName())){
				return true;
			}
		}
		return false;
	}
	
	public List<String> listFileNamesByDir(File dir){
		List<String> nameList = new ArrayList<String>();
		File[] files = dir.listFiles();
		for(File f : files){
			nameList.add(f.getName());
		}
		return nameList;
	}
	
	public File getUserSavedDirOfTestCase(String username){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String userDir = System.getProperty("user.dir");
		String shortName = username.substring(0, username.indexOf("@"));
		File savedDir = new File(userDir + File.separator + "testcase" + File.separator + shortName +
				File.separator + sdf.format(new Date()));
		return savedDir;
	}
	
	public File getCurrentTestResultDir(String username){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String userDir = System.getProperty("user.dir");
		String shortName = username.substring(0, username.indexOf("@"));
		File savedDir = new File(userDir + File.separator + "TestResult" + File.separator + shortName +
				File.separator + sdf.format(new Date()));
		return savedDir;
	}
	
	public File getUserSavedDirOfTestResult(String username){
		String userDir = System.getProperty("user.dir");
		String shortName = username.substring(0, username.indexOf("@"));
		File savedDir = new File(userDir + File.separator + "TestResult" + File.separator + shortName);
		return savedDir;
	}
	
	public JSONObject getTestCaseNumberWithFileName(String username){
		JSONObject caseJson = new JSONObject();
		File savedDir = getUserSavedDirOfTestCase(username);
		List<String> fnList = new ArrayList<String>();
		fnList =  listFileNamesByDir(savedDir);
		caseJson = excelIO.getTestCaseNameAndNumJSON(savedDir, fnList);
		
		return caseJson;
	}
	
	public int getTotalTestCaseNumber(String username){
		File savedDir = getUserSavedDirOfTestCase(username);
		List<String> fnList = new ArrayList<String>();
		fnList =  listFileNamesByDir(savedDir);
		int size = excelIO.getTestCaseTaskSize(savedDir, fnList, username);
		
		return size;
	}

/*	public String getReportInfoByType(String username, String type) {
		String reportInfo = null;
		if(ReportEnum.CURRENTREPORT.getType().equalsIgnoreCase(type))
			return reportVO.getCurrentReportPath();
		
		return reportInfo;
	}*/
	
	public List<String> listReportFilePath(File dir){
		List<String> reportFile = new ArrayList<String>();
		File []files = dir.listFiles();
		for(File f : files){
			reportFile.add(f.getAbsolutePath());
		}
		return reportFile;
	}
	
	
	public List<String> getReportListByType(String username, String type) {
		List<String> reportList = new ArrayList<String>();
		File reportDir = getUserSavedDirOfTestResult(username);
		if(ReportEnum.WEEKREPORT.getType().equalsIgnoreCase(type)){
			Calendar weekCal = Calendar.getInstance();
			weekCal.add(Calendar.DAY_OF_MONTH, -7);
			Date oneWeekBefore = weekCal.getTime();
			reportList = getReportFileListWithGivenDate(reportDir, oneWeekBefore);
		}else if(ReportEnum.MONTHREPORT.getType().equalsIgnoreCase(type)){
			Calendar monthCal = Calendar.getInstance();
			monthCal.add(Calendar.MONTH, -1);
			Date oneMonthBefore = monthCal.getTime();
			reportList = getReportFileListWithGivenDate(reportDir, oneMonthBefore);
		}else if(ReportEnum.YEARREPORT.getType().equalsIgnoreCase(type)){
			Calendar yearCal = Calendar.getInstance();
			yearCal.add(Calendar.YEAR, -1);
			Date oneYearBefore = yearCal.getTime();
			reportList = getReportFileListWithGivenDate(reportDir, oneYearBefore);
		}else if(ReportEnum.ALLREPORT.getType().equalsIgnoreCase(type)){
			Calendar cal = Calendar.getInstance();
			cal.set(1900, 1, 1);
			reportList = getReportFileListWithGivenDate(reportDir, cal.getTime());
		}
		
		return reportList;
	}
	
	private List<String> getReportFileListWithGivenDate(File reportDir, Date date) {
		List<String> reportFileList = new ArrayList<String>();
		File[] files = reportDir.listFiles();
		for(File f : files){
			String fileName = f.getName();
			try {
				Date reportDate = sdf.parse(fileName);
				if(reportDate.after(date)){
					List<String> dateReportFileList = listReportFilePath(new File(reportDir, fileName));
					for(String s : dateReportFileList)
						reportFileList.add(s);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return reportFileList;
	}

	public Map<String, Object> getReportInfo(String username) {
		Map<String, Object> reportInfo = new HashMap<String, Object>();
		reportInfo.put("current", reportVO.getPathNameMap().get(username));
		reportInfo.put("weekly", getReportListByType(username, ReportEnum.WEEKREPORT.getType()));
		reportInfo.put("monthly", getReportListByType(username, ReportEnum.MONTHREPORT.getType()));
		reportInfo.put("yearly", getReportListByType(username, ReportEnum.YEARREPORT.getType()));
		reportInfo.put("all", getReportListByType(username, ReportEnum.ALLREPORT.getType()));
		
		return reportInfo;
	}
	
	public List<ReportVO> getCurrentReportDetail(String username) {
		return excelIO.getCurrentReportDetail(reportVO.getPathNameMap().get(username));
	}
	
}
