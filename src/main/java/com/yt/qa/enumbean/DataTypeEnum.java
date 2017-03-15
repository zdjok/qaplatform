package com.yt.qa.enumbean;

/**
 * @author zhengdejing
 *
 */
public enum DataTypeEnum {
	BASIC_DATA_TYPE_BYTE("byte"),BASIC_DATA_TYPE_CHAR("char"),BASIC_DATA_TYPE_SHORT("short"),BASIC_DATA_TYPE_INT("int"),
	BASIC_DATA_TYPE_LONG("long"),BASIC_DATA_TYPE_FLOAT("float"),BASIC_DATA_TYPE_DOUBLE("double"),
	BASIC_DATA_TYPE_BOOLEAN("boolean"),BASIC_DATA_TYPE_STRING("String"),BASIC_DATA_TYPE_INTEGER("Integer"),
	BASIC_DATA_TYPE_LIST("List");
	
	private String dataType;
	
	private DataTypeEnum(String dataType){
		this.dataType = dataType;
	}
	
	public String getDataType(){
		return dataType;
	}
}
