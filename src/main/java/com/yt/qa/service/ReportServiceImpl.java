package com.yt.qa.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yt.qa.core.ReportVO;
import com.yt.qa.util.FileUtils;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	FileUtils fileUtils;
	
	@Override
	public Map<String, Object> getReportInfo(String username) {
		return fileUtils.getReportInfo(username);
	}

	@Override
	public List<ReportVO> getCurrentReportDetail(String username) {
		return fileUtils.getCurrentReportDetail(username);
	}

}
