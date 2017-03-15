package com.yt.qa.service;

import java.util.List;

import com.yt.qa.entity.UserDO;

/**
 * @author zhengdejing
 *
 */
public interface UserService {
	public UserDO getUser(String username, String password);
	
	public List<UserDO> listUsers();
	
	public List<UserDO> listUsers(String username);
	
	public void addUser(String name, String password, String email);
	
	public void updatePassword(String email, String password);
}
