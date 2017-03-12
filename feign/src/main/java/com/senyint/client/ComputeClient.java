package com.senyint.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("compute-service")
public interface ComputeClient {

	@RequestMapping("/test")
	String test(@RequestParam(value = "a") String a);
//	String test(@RequestParam String a);
}
