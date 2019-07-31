package com.datalkz.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@AuditableBean(module="SECURITY")
@RestController
public class LoginController {

//	@RequestMapping(method = {RequestMethod.POST}, value = "/login.json")
//	public void login(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
//		System.out.println(requestBody);
//	}
	
	@RequestMapping(method = {RequestMethod.GET}, value = "/login.json")
	public void login(HttpServletRequest request, HttpServletResponse response, @RequestParam("userName") String requestBody) {
		System.out.println(requestBody);
	}

}
