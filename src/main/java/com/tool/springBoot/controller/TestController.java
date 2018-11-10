package com.tool.springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tool.springBoot.service.TestService;


@Controller
public class TestController {

	@Autowired
	private TestService  testService;
	
	@RequestMapping("/hello")
    public String index() {
		testService.testMybatis();
		return "hello";
    }
	@RequestMapping("/userLogin")
    public String userLogin() {
//		return testService.myTest();
		
		return "userLogin";
    }
}
