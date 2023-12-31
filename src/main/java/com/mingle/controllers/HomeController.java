package com.mingle.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping("/")
	public String home() {
		return "forward:/index.html";
	}
	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<String> exceptionHandler(Exception e) {
//		logger.error(e.getMessage());
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//	}
}
