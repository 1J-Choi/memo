package com.memo.interceptor;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PermissionInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {
		// 요청 URI path
		String uri = request.getRequestURI();
		log.info("[########### preHandle] uri:{}", uri);
		
		// 로그인 여부
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		
		// /post로 시작 && 비로그인 => 로그인 페이지로 이동, 컨트롤러 수행 방지
		if (uri.startsWith("/post") && userId == null) {
			// redirect
			response.sendRedirect("/user/sign-in-view");
			
			// 원래 가려 했었던 컨트롤러 수행 방지
			return false;
		}
		
		// /user로 시작 && 로그인 => 글목록 페이지로 이동, 컨트롤러 수행 방지
		if (uri.startsWith("/user") && userId != null) {
			// redirect
			response.sendRedirect("/post/post-list-view");
			
			// 원래 가려 했었던 컨트롤러 수행 방지
			return false;
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, 
			ModelAndView mav) {
		// view와 model이 있다는 것 => html 해석되기 전임
		log.info("[$$$$$$$$$$$ postHandle]");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, 
			Exception ex) {
		// html의 렌더링이 완성된 상태
		log.info("[************** afterCompletion]");
	}
}
