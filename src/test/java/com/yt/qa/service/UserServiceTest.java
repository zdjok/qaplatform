package com.yt.qa.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yt.qa.entity.UserDO;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
	@Autowired
	UserService service;
	
	@Test
	public void test() {
		List<UserDO> list = service.listUsers();
		System.out.println(list);
	}

}
