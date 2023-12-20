package com.mingle.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {
	@GetMapping("/**")
	public String home() {
		return "forward:/index.html";
	}
	
//	@ResponseBody
//	@GetMapping("/member")
//	public String test() {
//		System.out.println("밍글");
//		return "ok";
//	}
}
