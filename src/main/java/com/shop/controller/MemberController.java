package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
@Controller
public class MemberController {
	
	//로그인 폼 요청
	@GetMapping("/login")
	public String loginForm() {
		return "member/loginForm";
	}
	
	//회원 가입 폼 요청
	@GetMapping("/new")
	public String memberForm() {
		return "member/memberForm";
	}
}
