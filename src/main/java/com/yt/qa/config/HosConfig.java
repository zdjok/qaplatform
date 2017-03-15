package com.yt.qa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//使用@ConfigurationProperties注解
/**
 * @author zhengdejing
 *
 */
@ConfigurationProperties(prefix = "server")
@Component
public class HosConfig {
	private String udbAuth;
	private String udbResource;
	private String udbResPath;
	private String udbAuthPath;
	private String medRestApiHost;
	private String medPath;
	private String port;
	private String hosId;
	private String username;
	private String password;
	private String usId;
	private String publicKey;
	private String privateKey;
	private String clientId;
	private String clientSecret;

	public String getUdbAuth() {
		return udbAuth;
	}

	public void setUdbAuth(String udbAuth) {
		this.udbAuth = udbAuth;
	}

	public String getUdbResource() {
		return udbResource;
	}

	public void setUdbResource(String udbResource) {
		this.udbResource = udbResource;
	}

	public String getUdbResPath() {
		return udbResPath;
	}

	public void setUdbResPath(String udbResPath) {
		this.udbResPath = udbResPath;
	}

	public String getUdbAuthPath() {
		return udbAuthPath;
	}

	public void setUdbAuthPath(String udbAuthPath) {
		this.udbAuthPath = udbAuthPath;
	}

	public String getMedRestApiHost() {
		return medRestApiHost;
	}

	public void setMedRestApiHost(String medRestApiHost) {
		this.medRestApiHost = medRestApiHost;
	}

	public String getMedPath() {
		return medPath;
	}

	public void setMedPath(String medPath) {
		this.medPath = medPath;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsId() {
		return usId;
	}

	public void setUsId(String usId) {
		this.usId = usId;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

}
