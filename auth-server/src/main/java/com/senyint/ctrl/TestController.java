package com.senyint.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	@RequestMapping("/login")
	public String test(){
		return "login";
	}
	
	@RequestMapping("/")
	public String loginsucc(){
		return "loginsucc";
	}

}
