package com.tool.springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tool.springBoot.service.TestService;


@RestController
public class TestController {

	@Autowired
	private TestService  testService;
	
	@RequestMapping("/hello")
    public String index() {
		return testService.myTest();
    }
}
