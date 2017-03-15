package com.yt.qa.enumbean;

public enum ReportEnum {
	CURRENTREPORT("CURRENT"),WEEKREPORT("WEEKLY"),MONTHREPORT("MONTHLY"),YEARREPORT("YEARLY"),ALLREPORT("ALL");
	
	private String type;
	
	private ReportEnum(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
}
