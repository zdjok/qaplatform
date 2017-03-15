package com.yt.qa.servlet;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;


/**
 * druid过滤器
 * @author zhengdejing
 *
 */
@WebFilter(filterName="DruidWebStatFilter",urlPatterns="/*",
			initParams={
					@WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")
			}
		)
			
public class DruidStatFilter extends WebStatFilter {

}
