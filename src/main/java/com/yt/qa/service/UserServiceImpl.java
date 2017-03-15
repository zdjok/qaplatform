package com.yt.qa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yt.qa.dao.UserMapper;
import com.yt.qa.entity.UserDO;

/**
 * @author zhengdejing
 *
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Override
	public UserDO getUser(String username, String password) {
		return userMapper.findUser(username, password);
	}
	
	@Override
	public List<UserDO> listUsers(){
		return userMapper.findUsers();
	}
	
	@Override
	public List<UserDO> listUsers(String username){
		return userMapper.findUserByUserName(username);
	}
	
	@Override
	public void addUser(String name, String password, String email){
		userMapper.addUser(name, password, email);
	}
	
	public void updatePassword(String email, String password){
		userMapper.updatePassword(email, password);
	}

}
