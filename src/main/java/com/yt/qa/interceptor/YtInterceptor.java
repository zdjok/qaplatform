package com.yt.qa.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class YtInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler)throws Exception{
		// 在controller方法调用前打印信息
        System.out.println("This is interceptor.");
        // 返回true，将强求继续传递（传递到下一个拦截器，没有其它拦截器了，则传递给Controller）
        return true;
	}
}
