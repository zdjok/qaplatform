package com.yt.qa.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yt.qa.core.ReportVO;
import com.yt.qa.core.TestCaseTask;
import com.yt.qa.enumbean.DataTypeEnum;
import com.yt.qa.enumbean.TestCaseEnum;
import com.yt.qa.enumbean.TestResultEnum;
import com.yt.qa.util.Common;
import com.yt.qa.util.FileUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * excel读写 
 * 
 * @author zhengdejing
 *
 */
@Component
public class ExcelIO {
	
	Logger logger = Logger.getLogger(ExcelIO.class);

	private Workbook book;
	private Sheet sheet;
	private InputStream in;
	private File resultFile;
	private static final int COLUMN_NUM = 7;

	public void initExcel(String file) {
		try {
			in = new FileInputStream(file);
			if (is2007Excel(file))
				book = new XSSFWorkbook(in);
			else
				book = new HSSFWorkbook(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * @Description: 获取测试用例excel名称及对应的用例数量
	 * @param dir:
	 *            The direction of test case
	 * @param fileList:
	 *            test case name
	 * @return
	 */
	public JSONObject getTestCaseNameAndNumJSON(File dir, List<String> fileList) {
		JSONObject json = new JSONObject();
		int total = 0;
		for (String f : fileList) {
			initExcel(dir.getAbsolutePath() + File.separator + f);
			int sheetNum = book.getNumberOfSheets();
			int sum = 0;
			for (int i = 0; i < sheetNum; i++) {
				Sheet sheet = book.getSheetAt(i);
				int rowNum = sheet.getLastRowNum();
				sum += rowNum;
			}
			total += sum;
			json.put(f, sum);
		}
		json.put("total", total);
		return json;
	}

	/**
	 * 提取测试用例任务
	 * @param dir
	 * @param fileList
	 * @return
	 */
	public List<TestCaseTask> getTestCaseTask(File dir, List<String> fileList, String userName) {
		List<TestCaseTask> taskList = new LinkedList<TestCaseTask>();
		for (String f : fileList) {
			initExcel(dir.getAbsolutePath() + File.separator + f);
			int sheetNum = book.getNumberOfSheets();
			for (int i = 0; i < sheetNum; i++) {
				Sheet sheet = book.getSheetAt(i);
				System.out.println(sheet.getSheetName());
				int rowNum = sheet.getLastRowNum();
				for (int j = 1; j <= rowNum; j++) {
					TestCaseTask testCase = new TestCaseTask();
					testCase.setUserName(userName);
					testCase.setExcelFile(dir.getAbsolutePath() + File.separator + f);
					testCase.setSheetName(sheet.getSheetName());
					String caseName = getCellValueByRowNumAndParamName(sheet, j , TestCaseEnum.CASENAMETITLE.getName());
					testCase.setCaseName(caseName);
					String method = getCellValueByRowNumAndParamName(sheet, j, TestCaseEnum.METHOD.getName()) == null
							? TestCaseEnum.DEFAULTMOTHOD.getName()
							: getCellValueByRowNumAndParamName(sheet, j, TestCaseEnum.METHOD.getName());
					testCase.setMethod(method);
					String path = getCellValueByRowNumAndParamName(sheet, j, TestCaseEnum.PATH.getName());
					testCase.setPath(path);
					String port = getCellValueByRowNumAndParamName(sheet, j, TestCaseEnum.PORT.getName()) == null
							? TestCaseEnum.DEFAULTMOTHOD.getName()
							: getCellValueByRowNumAndParamName(sheet, j, TestCaseEnum.PORT.getName());
					testCase.setPort(port);
					String expectResult = getCellValueByRowNumAndParamName(sheet, j,
							TestCaseEnum.EXPECTRESULT.getName());
					testCase.setExpectResult(expectResult);
					JSONObject inputParams = getJSONParamsByCaseRow(sheet, j);
					testCase.setInputParams(inputParams);
					taskList.add(testCase);
				}
			}
		}
		return taskList;
	}
	
	public int getTestCaseTaskSize(File dir, List<String> fileList, String userName){
		return getTestCaseTask(dir, fileList, userName).size();
	}

	/**
	 * 根据参数名称和行号获取对应的单元格数值
	 * 
	 * @param sheetName
	 *            excel表名
	 * @param rowNum
	 *            测试用例行号
	 * @param paramName
	 *            参数名称
	 * @return
	 */
	public String getCellValueByRowNumAndParamName(Sheet sheet, int rowNum, String paramName) {
		if (sheet == null)
			return null;
		Row row0 = sheet.getRow(0);
		Row rown = sheet.getRow(rowNum);
		int cellNum = row0.getLastCellNum();
		int j = 0;
		for (int i = 0; i < cellNum; i++) {
			if (paramName.equalsIgnoreCase(row0.getCell(i).getStringCellValue())) {
				j = i;
				break;
			}
		}
/*		if (j == 0) {
			return null;
		}*/
		String value = null;
		Cell celln = rown.getCell(j);
		value = getCellValue(celln);
		return value;
	}
	
	public void setCellValueByRowNumAndTitle(Sheet sheet, int rowNum, String title, String value) {
		if (sheet == null)
			throw new IllegalArgumentException("Sheet字段不能为空");
		Row row0 = sheet.getRow(0);
		Row rown = sheet.getRow(rowNum);
		int cellNum = row0.getLastCellNum();
		int j = 0;
		for (int i = 0; i < cellNum; i++) {
			if (title.equalsIgnoreCase(row0.getCell(i).getStringCellValue())) {
				j = i;
				break;
			}
		}
		if(title.equalsIgnoreCase(TestResultEnum.TESTRESULT.getTitle()) 
				&& TestResultEnum.PASS.getTitle().equalsIgnoreCase(value)){
			Cell celln = rown.getCell(j);
			celln.setCellStyle(getPassCellStyle(sheet));
			celln.setCellValue(value);
		}else if(title.equalsIgnoreCase(TestResultEnum.TESTRESULT.getTitle()) 
				&& TestResultEnum.FAIL.getTitle().equalsIgnoreCase(value)){
			Cell celln = rown.getCell(j);
			celln.setCellStyle(getFailCellStyle(sheet));
			celln.setCellValue(value);
		}else{
			Cell celln = rown.getCell(j);
			celln.setCellStyle(getDefaultCellStyle(sheet));
			celln.setCellValue(value);
		}
	}

	/**
	 * 获取单元格数值
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellValue(Cell cell) {
		String value = null;
		if (cell == null)
			value = null;
		else {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				value = null;
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = String.valueOf(cell.getBooleanCellValue()).trim();
				break;
			case Cell.CELL_TYPE_ERROR:
				value = String.valueOf(cell.getErrorCellValue()).trim();
				break;
			case Cell.CELL_TYPE_FORMULA:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date != null)
						value = new SimpleDateFormat("yyyy-MM-dd").format(date);
					else
						value = "";
				} else {
					value = cell.getStringCellValue();
				}
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date != null)
						value = new SimpleDateFormat("yyyy-MM-dd").format(date);
					else
						value = "";
				} else {
					value = new DecimalFormat("0").format(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				if (value == null || value.length() == 0)
					value = null;
				break;
			default:
				value = null;
			}
		}
		return value;
	}

	
	public JSONObject getJSONParamsByCaseRow(Sheet sheet, int caseRowNum) {
		return getJSONParamsByCaseRow(sheet, caseRowNum, 0);
	}

	/**
	 * 根据测试用例名称获取出入参JSON对象
	 * 
	 * @param sheetName
	 *            excel表名
	 * @param caseName
	 *            测试用例名称
	 * @param type
	 *            获取入参传0，获取出参传1
	 * @return JSONObject
	 */
	public JSONObject getJSONParamsByCaseRow(Sheet sheet, int caseRowNum, int size) {
		boolean hasJSONArray = false;
		boolean hasJSON = false;
		String arrayKey = null;
		String jsonKey = null;
		Comment comment = null;
		String paramType = null;
		if (sheet == null) {
			throw new IllegalArgumentException("入参Sheet不能为空");
		}
		
		int startColumnNum = getColumnNumByTitle(sheet, TestCaseEnum.INPUTSIZE.getName());
		int numberOfInputParams = getCellValueByRowNumAndParamName(sheet, caseRowNum,
				TestCaseEnum.INPUTSIZE.getName()) == null ? 1
						: Integer.valueOf(
								getCellValueByRowNumAndParamName(sheet, caseRowNum, TestCaseEnum.INPUTSIZE.getName()));
		int endColumnNum = startColumnNum + numberOfInputParams;

		JSONObject jsonParams = new JSONObject();
		Row row0 = sheet.getRow(0); // 取得第一行实例，用于取得第一行的名称
		for (int i = startColumnNum+1; i <= endColumnNum; i++) {
			Cell cell0 = row0.getCell(i);
			String paramName = cell0.getStringCellValue();
			String value = getCellValueByRowNumAndParamName(sheet, caseRowNum, paramName);

			comment = cell0.getCellComment();// 获取参数注释内容，用于判断参数类型组装json
			if (comment == null) {
				paramType = DataTypeEnum.BASIC_DATA_TYPE_STRING.getDataType();
			} else {
				String[] strList = comment.getString().toString().trim().split(";");
				if (strList.length == size + 1) {
					paramType = strList[size];
				} else if (strList.length > size + 1) {
					String ty = strList[size];
					if (value == null)
						continue;
					String keyName = ty.substring(ty.indexOf("_") + 1);
					if ("common".equalsIgnoreCase(keyName))
						continue;
					if (ty.startsWith("LIST_")) {
						hasJSONArray = true;
						arrayKey = keyName;
					}
					if (ty.startsWith("JSON_")) {
						hasJSON = true;
						jsonKey = keyName;
					}
					continue;
				}
			}

			if (value == null || "NULL".equals(value.trim()))
				continue;
			if (value != null && value.length() > 0)
				value = value.trim();
			String valueType = getCellValueTypeByRowNumAndParamNameAndColumnNum(sheet, caseRowNum, i, paramName);
			if (valueType == null)
				jsonParams.put(paramName, Common.returnConvertedDataAccordingType(paramType, value));
			else
				jsonParams.put(paramName, Common.returnConvertedDataAccordingType(valueType, value));
		}
		if (hasJSON) {
			jsonParams.put(jsonKey, getJSONParamsByCaseRow(sheet, caseRowNum, size + 1));
		}
		if (hasJSONArray) {
			jsonParams.put(arrayKey, getJSONArrayByCaseRow(sheet, caseRowNum, size + 1));
		}

		return jsonParams;
	}

	/**
	 * 根据字段名称查找所在列号
	 * 
	 * @param sheet
	 * @param title
	 * @return
	 */
	private int getColumnNumByTitle(Sheet sheet, String title) {
		int index = 0;
		if (sheet == null) {
			throw new IllegalArgumentException("入参Sheet不能为空");
		}
		if (title == null || title.trim().isEmpty()) {
			throw new IllegalArgumentException("入参CaseName不能为空");
		}
		Row row0 = sheet.getRow(0);
		int totalColumnNum = row0.getLastCellNum();
		for (int i = 0; i < totalColumnNum; i++) {
			Cell celli = row0.getCell(i);
			if (title.equalsIgnoreCase(celli.getStringCellValue())) {
				index = i;
				break;
			}
		}
		return index;
	}

	public JSONArray getJSONArrayByCaseRow(Sheet sheet, int caseRowNum, int size) {
		int count = 0;
		JSONArray array = new JSONArray();
		Comment comment = null;
		Map<String, String> paramType = new HashMap<String, String>();
		Map<String, List<String>> paramValue = new HashMap<String, List<String>>();

		if (sheet == null)
			throw new IllegalArgumentException("入参Sheet不能为空");

		int startColumnNum = getColumnNumByTitle(sheet, TestCaseEnum.INPUTSIZE.getName());
		int numberOfInputParams = getCellValueByRowNumAndParamName(sheet, caseRowNum,
				TestCaseEnum.INPUTSIZE.getName()) == null ? 1
						: Integer.valueOf(
								getCellValueByRowNumAndParamName(sheet, caseRowNum, TestCaseEnum.INPUTSIZE.getName()));
		int endColumnNum = startColumnNum + numberOfInputParams;

		Row row0 = sheet.getRow(0);
		Row rown = sheet.getRow(caseRowNum);
		for (int i = startColumnNum; i < endColumnNum; i++) {
			Cell cell0 = row0.getCell(i);
			comment = cell0.getCellComment();// 获取参数注释内容，用于判断参数类型组装json
			if (comment == null) {
				continue;
			}
			String[] strList = comment.getString().toString().trim().split(";");
			if (strList.length == size + 1) {
				String pType = strList[size];
				String paramName = cell0.getStringCellValue();
				paramType.put(paramName, pType);
				Cell celln = rown.getCell(i);
				if (celln != null) {
					// String v = celln.getStringCellValue().trim();
					String v = getCellValue(celln);
					if (v != null && v.startsWith("[") && v.endsWith("]")) {
						v = v.substring(1, v.length() - 1);
					}
					if (v != null && v.length() > 0) {
						List<String> list = Arrays.asList(v.split(","));
						paramValue.put(paramName, list);
					}
				}
			}
		}

		count = 1;
		for (int i = 0; i < count && paramValue.size() > 0; i++) {
			JSONObject json = new JSONObject();
			for (Entry<String, List<String>> entry : paramValue.entrySet()) {
				String key = entry.getKey();
				String pType = paramType.get(key);
				String strValue = paramValue.get(key).get(i).trim();
				if (strValue == null || "NULL".equals(strValue))
					continue; // 2016-06-14 by zdj
				if (strValue != null && strValue.length() > 0) // 2016-06-06 by
																// zdj
					json.put(key, Common.returnConvertedDataAccordingType(pType, strValue));
			}
			array.add(json);
		}
		return array;
	}


	/**
	 * @description 根据测试用例名称获取对应的行号
	 * @param sheetName
	 *            excel表名
	 * @param caseName
	 *            测试用例名称
	 * @return
	 */
	public int getRowNumByCaseName(String sheetName, String caseName) {
		int rowNum = -1;
		Sheet sheet = book.getSheet(sheetName);
		if (sheet == null)
			return rowNum;
		int maxRowNum = sheet.getLastRowNum();
		if (maxRowNum > 0) {
			for (int i = 0; i <= maxRowNum; i++) {
				Row rowi = sheet.getRow(i);
				if (rowi == null)
					continue;
				Cell cell0 = rowi.getCell(0);
				if (cell0 == null)
					continue;
				String name = cell0.getStringCellValue();
				if (caseName.equalsIgnoreCase(name)) {
					rowNum = i;
					break;
				}
			}
		}
		return rowNum;
	}


	/**
	 * 根据参数名称、列号和行号获取对应的单元格数值
	 * 
	 * @param sheetName
	 *            excel表名
	 * @param rowNum
	 *            测试用例行号
	 * @param columnNum
	 *            参数所在列号
	 * @param paramName
	 *            参数名称
	 * @return
	 */
	public String getCellValueByRowNumAndParamNameAndColumnNum(String sheetName, int rowNum, int columnNum,
			String paramName) {
		sheet = book.getSheet(sheetName);
		if (sheet == null)
			return null;
		Row rown = sheet.getRow(rowNum);
		String value = null;
		Cell celln = rown.getCell(columnNum);
		value = getCellValue(celln);
		return value;
	}

	/**
	 * 根据测试用例行号、参数所在列号获取参数值的类型，用于区分传入参数类型和实际定义类型不一致的情况
	 * 
	 * @param sheetName
	 *            excel表名
	 * @param rowNum
	 *            行号
	 * @param columnNum
	 *            参数所在列号
	 * @param paramName
	 *            参数名称
	 * @return
	 */
	public String getCellValueTypeByRowNumAndParamNameAndColumnNum(Sheet sheet, int rowNum, int columnNum,
			String paramName) {
		if (sheet == null)
			throw new IllegalArgumentException("入参Sheet不能为空");
		Row rown = sheet.getRow(rowNum);
		String valueType = null;
		Cell celln = rown.getCell(columnNum);
		if (celln == null)
			valueType = null;
		else {
			Comment comment = celln.getCellComment();
			if (comment == null)
				valueType = null;
			else
				valueType = comment.getString().toString().trim();
		}

		return valueType;
	}


	/**
	 * @description 判断是否07及以上excel文件格式
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public static boolean is2007Excel(String fileName) {
		if (fileName.matches("^.*.(?:xlsx)$"))
			return true;
		else
			return false;
	}

	/**
	 * 获取行数
	 * 
	 * @param sheetName
	 * @return
	 */
	public int getRowNum(String sheetName) {
		return book.getSheet(sheetName).getLastRowNum();
	}

	/**
	 * 获取列数
	 * 
	 * @param sheetName
	 * @return
	 */
	public int getColumnNum(String sheetName) {
		return book.getSheet(sheetName).getRow(0).getLastCellNum();
	}

	/**
	 * @description 关闭excel文件
	 */
	public void close() {
		try {
			if (book != null) {
				book.close();
				in.close();
				book = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**@Description：获取单元格注释
	 * @param cell
	 * @return
	 */
	protected final String getComment(Cell cell) {
		String comment = null;
		if (cell == null)
			return comment;
		Comment comments = cell.getCellComment();
		return comment = comments == null ? null : comments.getString().getString();
	}
	

	/**
	 * @Description:单元格内容是否为空
	 * @param cellValue
	 * @return
	 */
	protected boolean isEmpty(String cellValue) {
		if (cellValue == null || cellValue.trim().length() == 0)
			return true;
		return false;
	}
	
	/**
	 * PASS结果单元格字体样式
	 * @param sheet
	 * @return
	 */
	protected CellStyle getPassCellStyle(Sheet sheet){
		CellStyle passStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setColor(IndexedColors.BLUE.getIndex());
		font.setFontName("微软雅黑");
		font.setBold(true);
		passStyle.setFont(font);
		
		passStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		passStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		passStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		passStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		
		return passStyle;
	}
	
	/**
	 * FAIL结果单元格字体样式
	 * @param sheet
	 * @return
	 */
	protected CellStyle getFailCellStyle(Sheet sheet){
		CellStyle failedStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setColor(IndexedColors.RED.getIndex());
		font.setFontName("微软雅黑");
		font.setBold(true);
		font.setItalic(true);
		
		failedStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		failedStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		failedStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		failedStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		
		failedStyle.setFont(font);
		
		return failedStyle;
	}
	
	protected CellStyle getDefaultCellStyle(Sheet sheet){
		CellStyle defaultStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setFontName("微软雅黑");
		
		defaultStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		defaultStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		defaultStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		defaultStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		
		defaultStyle.setFont(font);
		
		return defaultStyle;
	}

	/**
	 * @Description: 创建测试报告模板
	 * @param username：登录用户名
	 */
	@SuppressWarnings({ "resource" })
	public String createTestResultFile(String username) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmSS");
		File resultDir = fileUtils.getCurrentTestResultDir(username);
		if(!resultDir.exists())	resultDir.mkdirs();
		resultFile = new File(resultDir, "TestResult_" + sdf.format(new Date()) + ".xlsx");
//		reportVo.setCurrentReportPath(resultFile.getAbsolutePath());
		reportVo.getPathNameMap().put(username, resultFile.getAbsolutePath());
		OutputStream fos = null;
		if(!resultFile.exists()){
			try {
				XSSFWorkbook book = new XSSFWorkbook();
				XSSFSheet sheet = book.createSheet(TestResultEnum.SHEETNAME.getTitle());
				
				sheet.setDefaultColumnWidth(20);
				
				//字体样式
				XSSFFont font = book.createFont();
				font.setFontName("微软雅黑");
				font.setBold(true);
				font.setFontHeight((short)230);
				
				//单元格样式
				CellStyle style = book.createCellStyle();
				style.setAlignment(CellStyle.ALIGN_CENTER);
				style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				//单元格边框
				style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
				style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
				style.setBorderRight(XSSFCellStyle.BORDER_THIN);
				style.setBorderTop(XSSFCellStyle.BORDER_THIN);
				
				//设置字体
				style.setFont(font);
				
				//创建测试报告模板
				Row row0 = sheet.createRow(0);
				for(int i=0; i<COLUMN_NUM; i++){
					Cell celli = row0.createCell(i);
					celli.setCellStyle(style);
					celli.setCellValue(TestResultEnum.getTitle(i));
				}
				fos = new FileOutputStream(resultFile);
				book.write(fos);
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				if(fos != null){
					try {
						fos.close();
						book.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return resultFile.getAbsolutePath();
		}
		return null;
	}
	

	/**
	 * @Description：将测试结果写入测试报告
	 * @param testResultMap：测试结果
	 */
	public void writeTestResult2Excel(Map<TestCaseTask, JSONObject> testResultMap) {
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>." + testResultMap);
		OutputStream os = null;
		try {
			book = new XSSFWorkbook(new FileInputStream(resultFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(book != null){
			Sheet sheet = book.getSheetAt(0);
			Set<Entry<TestCaseTask, JSONObject>> sets = testResultMap.entrySet();
			int i = 1;
			for(Iterator<Entry<TestCaseTask, JSONObject>> itr = sets.iterator(); itr.hasNext();){
				Entry<TestCaseTask, JSONObject> entry = itr.next();
				TestCaseTask task = entry.getKey();
				JSONObject res = entry.getValue();
				Row rowi = sheet.createRow(i);
				for(int j=0; j<COLUMN_NUM; j++){
					rowi.createCell(j);
				}
				String excelFileName = task.getExcelFile();
				setCellValueByRowNumAndTitle(sheet, i, TestResultEnum.MODULE.getTitle(),
						excelFileName.substring(excelFileName.lastIndexOf("\\") + 1, excelFileName.lastIndexOf(".")));
				setCellValueByRowNumAndTitle(sheet, i, TestResultEnum.INTERFACENAME.getTitle(), task.getSheetName());
				setCellValueByRowNumAndTitle(sheet, i, TestResultEnum.TESTCASENAME.getTitle(), task.getCaseName());
				String expectedResult = task.getExpectResult();
				if(expectedResult == null)	expectedResult = "";
				String actualResult = res.optString("result");
				setCellValueByRowNumAndTitle(sheet, i, TestResultEnum.EXPECTRESULT.getTitle(), expectedResult);
				setCellValueByRowNumAndTitle(sheet, i, TestResultEnum.ACTUALRESULT.getTitle(), actualResult);
				if(expectedResult.equalsIgnoreCase(actualResult))
					setCellValueByRowNumAndTitle(sheet, i, TestResultEnum.TESTRESULT.getTitle(),
							TestResultEnum.PASS.getTitle());
				else
					setCellValueByRowNumAndTitle(sheet, i, TestResultEnum.TESTRESULT.getTitle(),
							TestResultEnum.FAIL.getTitle());
				setCellValueByRowNumAndTitle(sheet, i, TestResultEnum.RESPONSE.getTitle(), res.toString());
				i++;
			}
		}
		try {
			os = new FileOutputStream(resultFile);
			book.write(os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try{
				if(os != null)
					os.close();
				if(book != null)
					book.close();
			} catch(IOException e){
				e.printStackTrace();
			}			
		}
	}

	public List<ReportVO> getCurrentReportDetail(String currentReportPath) {
		initExcel(currentReportPath);
		Sheet sheet = book.getSheet(TestResultEnum.SHEETNAME.getTitle());
		int rowNum = sheet.getLastRowNum();
		List<ReportVO> reportList = new ArrayList<ReportVO>();
		for(int i=1; i<=rowNum; i++){
			ReportVO reportVO = new ReportVO();
			reportVO.setModuleName(getCellValueByRowNumAndParamName(sheet, i, TestResultEnum.MODULE.getTitle()));
			reportVO.setApiName(getCellValueByRowNumAndParamName(sheet, i, TestResultEnum.INTERFACENAME.getTitle()));
			reportVO.setCaseName(getCellValueByRowNumAndParamName(sheet, i, TestResultEnum.TESTCASENAME.getTitle()));
			reportVO.setExpectedResult(getCellValueByRowNumAndParamName(sheet, i, TestResultEnum.EXPECTRESULT.getTitle()));
			reportVO.setActualResult(getCellValueByRowNumAndParamName(sheet, i, TestResultEnum.ACTUALRESULT.getTitle()));
			reportVO.setTestResult(getCellValueByRowNumAndParamName(sheet, i, TestResultEnum.TESTRESULT.getTitle()));
			reportVO.setResponse(getCellValueByRowNumAndParamName(sheet, i, TestResultEnum.RESPONSE.getTitle()));
			reportList.add(reportVO);
		}
		return reportList;
	}
	
	
	@Autowired
	FileUtils fileUtils;
	
	@Autowired
	ReportVO reportVo;

}
