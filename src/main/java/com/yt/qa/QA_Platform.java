package com.yt.qa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author zhengdejing
 *
 */
@SpringBootApplication
@MapperScan(basePackages={"com.yt.qa.dao"})
@ServletComponentScan
public class QA_Platform {
	public static void main(String[] args) {
		SpringApplication.run(QA_Platform.class, args);
	}
}
