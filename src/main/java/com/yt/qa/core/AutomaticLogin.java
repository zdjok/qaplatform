package com.yt.qa.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yt.qa.config.HosConfig;
import com.yt.qa.enumbean.DeviceModel;
import com.yt.qa.enumbean.TerminalType;
import com.yt.qa.util.RSA;

import net.sf.json.JSONObject;

@Component
public class AutomaticLogin {
	@Autowired
	HosConfig hosConfig;

	@Autowired
	RequestClient client;

	@Autowired
	UDB2HeaderEntity headerEntity;

	@Autowired
	UDB2LoginData uld;

	JSONObject req = new JSONObject();
	JSONObject res = new JSONObject();
	ResultSet rs = null;
	int terminalType;

	Logger logger = Logger.getLogger(AutomaticLogin.class);

	public int getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(int terminalType) {
		this.terminalType = terminalType;
	}

	/**
	 * UDB2.0 密码模式获取access_token
	 */
	public void udb2LoginWithPassword() {
		System.out.println(hosConfig.getClientSecret());
		JSONObject res = getUnicode();
		logger.info("getUnicode>>>>>>>>>>>>>>>>>>>>>>" + res);
		String unicode = (String) res.opt("unicode");
		long timeflag = Long.valueOf((String) res.opt("timelag"));

		// UDB2LoginData uld = new UDB2LoginData("password", unicode);
		uld.initLoginData("password", unicode);
		String udbUrl = hosConfig.getUdbAuth() + hosConfig.getUdbAuthPath() + "/oauth/token" + uld.toString();

		res = client.doUDBPost(udbUrl, req);
		logger.info("doLogin>>>>>>>>>>>>>>>>>>>>>>" + res);
		JSONObject temp = res.getJSONObject("data");
		String access_token = temp.getString("access_token");

		headerEntity.initHeaderEntity(unicode, access_token, String.valueOf(System.currentTimeMillis() - timeflag));
	}

	/**
	 * app设备信息上报及unicode获取
	 * 
	 * @return
	 */
	private JSONObject getUnicode() {
		long terminalTime = System.currentTimeMillis();
		String devModel = DeviceModel.MI;
		int terminalType = TerminalType.ANDROID;

		JSONObject paramJson = new JSONObject();
		paramJson.put("terminalTime", terminalTime);
		paramJson.put("devModel", devModel);
		paramJson.put("terminalType", terminalType);

		headerEntity.initHeaderEntity(paramJson.toString());
		JSONObject res = client.doUDBPost(hosConfig.getUdbAuth() + hosConfig.getUdbAuthPath() + "/r/10001/100",
				paramJson);
		res = res.optJSONObject("data");
		return res;
	}

	/**
	 * 需要单独搭建服务跳转，并且还要通过浏览器页面进行授权登录，暂无法实现
	 * 
	 * @param terminalType
	 */
	public void udb2LoginWithAuth(int terminalType) {

		UDB2LoginData uld = new UDB2LoginData();
		uld.initIUnicodeParams(terminalType, hosConfig.getUdbAuth() + hosConfig.getUdbAuthPath() + "/oauth/authorize");
		String redirectUrl = client.doGetAndFetchRedirectURL(
				hosConfig.getUdbAuth() + hosConfig.getUdbAuthPath() + "/r/unicode" + uld.toString(), "GET");
		System.out.println(redirectUrl);
		String unicode = redirectUrl.substring(redirectUrl.lastIndexOf('=') + 1);
		System.out.println("unicode in url: " + unicode);

		try {
			System.out.println("unicode decode: " + URLDecoder.decode(unicode, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		byte[] result = null;
		try {
			result = RSA.decrypt(RSA.loadPrivateKeyByStr(hosConfig.getPrivateKey()),
					Base64.decodeBase64(URLDecoder.decode(unicode, "UTF-8")));
			unicode = new String(result);
			System.out.println("unicode decrypt: " + unicode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		uld = new UDB2LoginData();
		uld.initICodeParams(unicode, hosConfig.getUdbAuth() + hosConfig.getUdbAuthPath() + "/oauth/token");

		redirectUrl = client.doGetAndFetchRedirectURL(
				hosConfig.getUdbAuth() + hosConfig.getUdbAuthPath() + "/oauth/authorize" + uld.toString(), "GET");
		System.out.println("redirectUrl = " + redirectUrl);
	}

	public void doUDB2Login() {
		udb2LoginWithPassword();
	}

}
