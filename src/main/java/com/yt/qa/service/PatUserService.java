package com.yt.qa.service;

import java.util.List;

import com.yt.qa.entity.PatUserDO;

/**
 * @author zhengdejing
 *
 */
public interface PatUserService {
	PatUserDO selectPatUserById(int id);
	
	List<PatUserDO> getAllPatUser();
}
