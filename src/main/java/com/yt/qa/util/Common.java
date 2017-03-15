package com.yt.qa.util;

import java.util.Arrays;

import com.yt.qa.enumbean.DataTypeEnum;

/**
 * @author zhengdejing
 *
 */
public class Common {
	
	/**
	 * 根据数据类型返回对应类型的数值
	 * @param dataType	数据类型
	 * @param value	数值
	 * @return
	 */
	public static Object returnConvertedDataAccordingType(String dataType, String value){
		Object obj = null;
		if(value == null || value.trim().length() == 0)
			return value;

		if(DataTypeEnum.BASIC_DATA_TYPE_STRING.getDataType().equalsIgnoreCase(dataType))
			obj = value;
		else if(DataTypeEnum.BASIC_DATA_TYPE_CHAR.getDataType().equalsIgnoreCase(dataType))
			obj = Character.valueOf(value.charAt(0));
		else if(DataTypeEnum.BASIC_DATA_TYPE_SHORT.getDataType().equalsIgnoreCase(dataType))
			obj = Short.valueOf(value);
		else if(DataTypeEnum.BASIC_DATA_TYPE_INT.getDataType().equalsIgnoreCase(dataType))
			obj = Integer.valueOf(value);
		else if(DataTypeEnum.BASIC_DATA_TYPE_INTEGER.getDataType().equalsIgnoreCase(dataType))
			obj = Integer.valueOf(value);
		else if(DataTypeEnum.BASIC_DATA_TYPE_LONG.getDataType().equalsIgnoreCase(dataType))
			obj = Long.valueOf(value);
		else if(DataTypeEnum.BASIC_DATA_TYPE_FLOAT.getDataType().equalsIgnoreCase(dataType))
			obj = Float.valueOf(value);
		else if(DataTypeEnum.BASIC_DATA_TYPE_DOUBLE.getDataType().equalsIgnoreCase(dataType))
			obj = Double.valueOf(value);
		else if(DataTypeEnum.BASIC_DATA_TYPE_BOOLEAN.getDataType().equalsIgnoreCase(dataType))
			obj = Boolean.valueOf(value);
		else if(DataTypeEnum.BASIC_DATA_TYPE_LIST.getDataType().equalsIgnoreCase(dataType)){
			if( value.startsWith("[") && value.endsWith("]") ){
				value = value.substring(1, value.length()-1).trim();
			}
			if(value.length() > 0 ){
				obj = Arrays.asList(value.split(","));
			}
		}			
		else
			obj = value;
		return obj;
	}

}
