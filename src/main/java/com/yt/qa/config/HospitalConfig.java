package com.yt.qa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//使用@PropertySource注解，使用的类中使用Environment env获取属性;
/**
 * @author zhengdejing
 *
 */
@Configuration
@PropertySource("classpath:config/hospitalconfig.properties")
@Component
public class HospitalConfig {
	@Value("${server.udbAuth}")
	private String udbAuth;
	
	@Value("${server.udbResource}")
	private String udbResource;
	
	@Value("${server.udbResPath}")
	private String udbResPath;
	
	@Value("${server.udbAuthPath}")
	private String udbAuthPath;
	
	@Value("${server.medRestApiHost}")
	private String medRestApiHost;
	
	@Value("${server.medPath}")
	private String medPath;
	
/*	@Value("${server.port}")
	private String port;*/
	
	@Value("${server.hosId}")
	private String hosId;
	
	@Value("${server.username}")
	private String username;
	
	@Value("${server.password}")
	private String password;
	
	@Value("${server.usId}")
	private String usId;
	
	@Value("${server.publicKey}")
	private String publicKey;
	
	@Value("${server.privateKey}")
	private String privateKey;
	
	@Value("${server.clientId}")
	private String clientId;
	
	@Value("${server.clientSecret}")
	private String clientSecret;
}
