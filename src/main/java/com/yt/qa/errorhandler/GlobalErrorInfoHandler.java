package com.yt.qa.errorhandler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorInfoHandler {
	
	@ExceptionHandler(value=GlobalErrorInfoException.class)
	public ResultBody errorHandlerOverJson(HttpServletRequest request, GlobalErrorInfoException exception){
		ErrorInfoInterface errorInfo = exception.getErrorInfoInterface();
		ResultBody result = new ResultBody(errorInfo);
		return result;
	}
}
