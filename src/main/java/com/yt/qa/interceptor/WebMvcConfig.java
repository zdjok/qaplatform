package com.yt.qa.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yt.qa.enumbean.SessionEnum;

/**
 * @author zhengdejing
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	Logger logger = Logger.getLogger(WebMvcConfig.class);
	
	@Bean
	public SecurityInterceptor getSecurityInterceptor(){
		return new SecurityInterceptor();
	}
	
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration interceptorReg = registry.addInterceptor(getSecurityInterceptor());
		interceptorReg.addPathPatterns("/**");
		interceptorReg.excludePathPatterns("/error");
		interceptorReg.excludePathPatterns("/login**");
		interceptorReg.excludePathPatterns("/doLogin");
		interceptorReg.excludePathPatterns("/register");
		interceptorReg.excludePathPatterns("/resetPassword");
	}

	private class SecurityInterceptor extends HandlerInterceptorAdapter {
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			HttpSession session = request.getSession();
			Object sessionValue = session.getAttribute(SessionEnum.SESSIONKEY.getSessionKey());
			if(sessionValue != null)
				return true;
			response.sendRedirect("/login");
			return false;
		}
	}
}
