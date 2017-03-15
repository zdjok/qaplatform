package com.yt.qa.dao;

import java.util.List;

import com.yt.qa.entity.PatUserDO;

/**
 * @author zhengdejing
 *
 */
public interface PatUserMapper {
	PatUserDO selectPatUserById(int id);
	
	List<PatUserDO> getAllPatUser();
}
