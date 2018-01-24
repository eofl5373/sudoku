package com.bnk.plus.config.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class DetectFrontUserInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger log_usual = LoggerFactory.getLogger("ADMIN-USUAL");
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		/**
		 * 접속자 IP 가져오기
		 */
		String ip = request.getHeader("X-FORWARDED-FOR"); 
		if (ip == null || ip.length() == 0) { ip = request.getHeader("Proxy-Client-IP"); }
		if (ip == null || ip.length() == 0) { ip = request.getHeader("WL-Proxy-Client-IP"); }		// 웹로직
		if (ip == null || ip.length() == 0) { ip = request.getRemoteAddr() ; }
		//log_usual.info("Session User : " + auth.getName() + "IP : " + ip + ", Request URL : " + CoTopComponent.getRequestURLAndQueryString(request));
		
		if(auth == null || "anonymousUser".equalsIgnoreCase(auth.getName())){
			// 2017-07-21 로그인되지 않은 사용자가 마이페이지로 이동할경우 로그인페이지로 리다이렉트
			String curUri = request.getRequestURI();
			if(curUri.contains("/my/") || curUri.contains("/memberModify")){
				//response.sendRedirect(request.getContextPath()+AppConstBean.SECURITY_LOGIN_PAGE);
			}
		}
		
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if(modelAndView != null){
//			modelAndView.addObject("context", request.getRequestURL());
		}
		super.postHandle(request, response, handler, modelAndView);
	}
}
