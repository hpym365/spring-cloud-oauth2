package com.senyint.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senyint.client.ComputeClient;

@RestController
public class FeignController {

	@Autowired
	ComputeClient computeClient;

	@RequestMapping("/test")
	public String test(@RequestParam String a) {
		return computeClient.test(a);
	}
}
