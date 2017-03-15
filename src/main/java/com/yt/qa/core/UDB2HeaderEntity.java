package com.yt.qa.core;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yt.qa.config.HosConfig;
import com.yt.qa.util.RSA;
import com.yt.qa.util.RSASignature;

/**
 * @author zhengdejing
 *
 */
@Component
public class UDB2HeaderEntity {
	@Autowired
	HosConfig hosConfig;
	
	String signature;
	String unicode;
	String client_id;
	String access_token;

/*	public UDB2HeaderEntity( String unicode,  String access_token, String content) {
		super();
		setSignature(content);
		setClient_id();
		setUnicode(unicode);
		setAccess_token(access_token);
	}*/
	
	public void initHeaderEntity(String unicode,  String access_token, String content){
		setSignature(content);
		setClient_id();
		setUnicode(unicode);
		setAccess_token(access_token);
	}
	
	public void initHeaderEntity(String content){
		signSignature(content);
		setClient_id();
	}

	public String getSignature() {
		return signature;
	}
	
	public void setSignature(String content){
		try {
/*			signature = Base64.encodeBase64String(RSA.encrypt(RSA.loadPublicKeyByStr(ConfigIO.getInstance().getPublicKey()),
					String.valueOf(System.currentTimeMillis()-60000L).getBytes()));*/
			signature = Base64.encodeBase64String(RSA.encrypt(RSA.loadPublicKeyByStr(hosConfig.getPublicKey()),
					content.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void signSignature(String content){
		try {
			signature = RSASignature.sign(content, hosConfig.getPrivateKey().trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUnicode() {
		return unicode;
	}
	
	public void setUnicode(String _unicode) {
		unicode = _unicode;
	}
	
	public String getClient_id() {
		return client_id;
	}
	
	public void setClient_id() {
		client_id = hosConfig.getClientId();
	}
	
	public String getAccess_token() {
		return access_token;
	}
	
	public void setAccess_token(String _access_token) {
		access_token = _access_token;
	}

	@Override
	public String toString(){
		return "signature : " + signature + ", unicode: " + unicode + ", client_id: " + client_id + ", access_token: " + access_token;
	}

}
