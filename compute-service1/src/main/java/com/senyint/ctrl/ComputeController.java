package com.senyint.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;

@RestController
public class ComputeController {

	@Autowired
	private EurekaClient client;

	@RequestMapping("/test")
	public String test(@RequestParam String a) {

		Applications applications = client.getApplications();
		List<Application> registeredApplications = applications.getRegisteredApplications();
		System.out.println("I am compute-service 1");
		return "I am 2223:" + a;
	}
}
