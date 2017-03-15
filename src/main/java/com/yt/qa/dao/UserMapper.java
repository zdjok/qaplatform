package com.yt.qa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yt.qa.entity.UserDO;

/**
 * @author zhengdejing
 *
 */
public interface UserMapper {
	public UserDO findUser(@Param("email") String username, @Param("password") String password);
	
	public List<UserDO> findUsers();
	
	public List<UserDO> findUserByUserName(@Param("email") String username);
	
	public void addUser(@Param("name") String name, @Param("password") String password, @Param("email") String email);
	
	public void updatePassword(@Param("email") String email, @Param("password") String password);
}
