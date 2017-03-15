package com.yt.qa.enumbean;

/**
 * @author zhengdejing
 *
 */
public enum ErrorMsgEnum {
	EXIST(1, "用户名已经存在！"), EMPTYPASSWORD(2, "密码不能为空！"), PASSWORDNOTMATCH(3, "两次输入的密码不一致！"), 
	INVALIDEMAIL(4,"用户名格式不正确！格式：xxx@xx.xx"), ERROR(5,"用户名或密码过长，添加失败，请重新注册！"),
	EMPTYEMAIL(6,"用户名不能为空"),NOTEXIST(7,"用户名不存在");
	
	private int id;
	private String msg;
	
	private ErrorMsgEnum(int id, String msg){
		this.id = id;
		this.msg = msg;
	}
	
	public int getId(){
		return id;
	}
	
	public String getMsg(){
		return msg;
	}
}
