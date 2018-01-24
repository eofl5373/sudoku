package com.bnk.plus.config.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.bnk.plus.config.AppConstBean;


public class AppAccessDeniedHandler implements AccessDeniedHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(AppAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest arg0, HttpServletResponse arg1, AccessDeniedException arg2) throws IOException, ServletException {
		logger.debug("Access Denied {'url':'"+arg0.getRequestURL()+"'}");
		
		// TODO: 161115 jy-seo 임시코드
		arg1.sendRedirect(arg0.getContextPath() + AppConstBean.SECURITY_FRONT_ACCESS_DENIED_PAGE);
	}

}
