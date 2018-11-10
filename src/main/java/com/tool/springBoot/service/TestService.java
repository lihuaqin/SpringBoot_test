package com.tool.springBoot.service;

import com.tool.springBoot.vo.User;

public interface TestService {

	public String myTest();
	
	public void testMybatis();
	public User getUserByName(String name);
}
