package com.memo.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	/**
	 * 회원가입 화면
	 * @return
	 */
	@GetMapping("/sign-up-view")
	public String signUpView() {
		// 가운데 레이아웃만 내려주면 전체 레이아웃과 함께 구성된다.
		return "user/signUp";
	}
	
	/**
	 * 로그인 화면
	 * @return
	 */
	@GetMapping("/sign-in-view")
	public String signInView() {
		// 가운데 레이아웃만 내려주면 전체 레이아웃과 함께 구성된다.
		return "user/signIn";
	}
}
