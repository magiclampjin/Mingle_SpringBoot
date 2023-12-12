package com.mingle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.services.MemberService;

@Controller
@RestController("/api/member")
public class MemberController {
	@Autowired
	private MemberService mServ;
}
