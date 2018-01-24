package com.bnk.plus.commons.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.bnk.plus.commons.components.CoTopComponent;
import com.bnk.plus.config.AppConstBean;

/**
 *
 * <pre>
 * AdviceController.java
 * </pre>
 *
 * 전체페이지에 적용되는 ControllerAdvice
 *
 * @author Administrator
 * @date 2015. 8. 27.
 */
@ControllerAdvice
public class AdviceController extends CoTopComponent{

	/**
		jsp reqest 자동 리턴
	 */
	@ModelAttribute("req")
	public HttpServletRequest getRequest(HttpServletRequest req){
		return req;
	}

	/**
			"현재 로그인 되어 있는 세션 ID" 를 일괄적으로 model에 담아 리턴
	 */
	@ModelAttribute("sessUserId")
	public String getSessUserId(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null){
			return auth.getName();
		}
		return "";
	}

	@ModelAttribute("pageTitlePrefix") public String pageTitlePrefix(){return AppConstBean.APP_NAME;}
	@ModelAttribute("context") public String context(HttpServletRequest request){return request.getRequestURL().toString();}
	
}
