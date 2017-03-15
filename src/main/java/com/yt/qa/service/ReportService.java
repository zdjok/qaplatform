package com.yt.qa.service;

import java.util.List;
import java.util.Map;

import com.yt.qa.core.ReportVO;

public interface ReportService {
	public Map<String, Object> getReportInfo(String username);
	
	public List<ReportVO> getCurrentReportDetail(String username);
}
