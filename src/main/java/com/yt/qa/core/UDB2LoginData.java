package com.yt.qa.core;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yt.qa.config.HosConfig;
import com.yt.qa.util.RSA;

/**
 * @author zhengdejing
 *
 */
@Component
public class UDB2LoginData {
	@Autowired
	HosConfig hosConfig;
	
	private String grant_type;
	private String client_id;
	private String client_secret;
	private String code;
	private String redirect_uri;
	private String unicode;
	private String scope;
	private String username;
	private String password;
	private String login_type;
	private String third_type;
	private String state;
	private int terminal_type = -1;
	private String response_type;
	
	
	public UDB2LoginData(){
		super();
	}
	
	/**
	 * 密码模式登录参数初始化
	 * @param grant_type
	 * @param unicode
	 */
	public UDB2LoginData(String grant_type, String unicode) {
		super();
		this.setUnicode(unicode);
		this.setGrant_type(grant_type);
		this.setClient_id();
		this.setClient_secret();
		this.setUsername();
		this.setPassword();
	}
	
	public void initLoginData(String grant_type, String unicode){
		this.setUnicode(unicode);
		this.setGrant_type(grant_type);
		this.setClient_id();
		this.setClient_secret();
		this.setUsername();
		this.setPassword();
	}

	
	/**
	 * 授权登录获取unicode接口参数初始化
	 * @param terminalType	终端
	 * @param fetchCodeUrl	重定向url
	 */
	public void initIUnicodeParams(int terminalType, String fetchCodeUrl) {
		this.setClient_id();
		this.setTerminal_type(terminalType);
		this.setRedirect_uri(fetchCodeUrl);
		this.setState();
	}
	
	/**
	 * 授权登录获取Code接口参数初始化
	 * @param unicode
	 * @param redirectUrl
	 */
	public void initICodeParams(String unicode, String redirectUrl) {
		this.setClient_id();
		this.setResponse_type();
		this.setUnicode(unicode);
		this.setRedirect_uri(redirectUrl);
		this.setState();
	}

	public String getGrant_type() {
		return grant_type;
	}
	
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	
	public String getClient_id() {
		return client_id;
	}
	
	public void setClient_id() {
		this.client_id = hosConfig.getClientId();
	}
	
	public String getClient_secret() {
		return client_secret;
	}
	
	public void setClient_secret() {
		this.client_secret = hosConfig.getClientSecret();
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getRedirect_uri() {
		return redirect_uri;
	}
	
	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}
	
	public String getUnicode() {
		return unicode;
	}
	
	public void setUnicode(String unicode) {
		this.unicode = unicode;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername() {
		this.username = hosConfig.getUsername();
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword() {
		try {
			this.password = Base64.encodeBase64String(RSA.encrypt(RSA.loadPublicKeyByStr(hosConfig.getPublicKey()),
					hosConfig.getPassword().getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getLogin_type() {
		return login_type;
	}
	
	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	
	public String getThird_type() {
		return third_type;
	}
	
	public void setThird_type(String third_type) {
		this.third_type = third_type;
	}
	
	public String getState() {
		return state;
	}

	public void setState() {
		this.state = String.valueOf(System.currentTimeMillis());
	}
	
	public int getTerminal_type() {
		return terminal_type;
	}

	public void setTerminal_type(int terminal_type) {
		this.terminal_type = terminal_type;
	}

	public String getResponse_type() {
		return response_type;
	}

	public void setResponse_type() {
		this.response_type = "code";
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder(200);
		sb.append("?");
		if(this.client_id != null && this.client_id.trim().length() > 0)
			sb.append("client_id=").append(client_id);
		if(this.client_secret != null && this.client_secret.trim().length() > 0)
			sb.append("&client_secret=").append(client_secret);
		if(this.grant_type != null && this.grant_type.trim().length() > 0)
			sb.append("&grant_type=").append(grant_type);
		if(this.scope != null && this.scope.trim().length() > 0)
			sb.append("&scope=").append(scope);
		if(this.username != null && this.username.trim().length() > 0)
			sb.append("&username=").append(username);
		if(this.password != null && this.password.trim().length() > 0)
			sb.append("&password=").append(password);
		if(this.unicode != null && this.unicode.trim().length() > 0)
			sb.append("&unicode=").append(unicode);
		if(this.login_type != null && this.login_type.trim().length() > 0)
			sb.append("&login_type=").append(login_type);
		if(this.third_type != null && this.third_type.trim().length() > 0)
			sb.append("&third_type=").append(third_type);
		if(this.state != null && this.state.trim().length() > 0)
			sb.append("&state=").append(state);
		if(this.terminal_type != -1)
			sb.append("&terminal_type=").append(terminal_type);
		if(this.redirect_uri != null && this.redirect_uri.trim().length()>0)
			sb.append("&redirect_uri=").append(redirect_uri);
		if(this.response_type != null && this.response_type.trim().length()>0)
			sb.append("&response_type=").append(response_type);
		
		return sb.toString();
	}
}
