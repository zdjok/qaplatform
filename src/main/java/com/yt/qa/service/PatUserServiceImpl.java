package com.yt.qa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yt.qa.dao.PatUserMapper;
import com.yt.qa.entity.PatUserDO;

/**
 * @author zhengdejing
 *
 */
@Service
public class PatUserServiceImpl implements PatUserService {

	@Autowired
	PatUserMapper patUserMapper;
	
	@Override
	public PatUserDO selectPatUserById(int id) {
		// TODO Auto-generated method stub
		return patUserMapper.selectPatUserById(id);
	}

	@Override
	public List<PatUserDO> getAllPatUser() {
		// TODO Auto-generated method stub
		return patUserMapper.getAllPatUser();
	}

}
