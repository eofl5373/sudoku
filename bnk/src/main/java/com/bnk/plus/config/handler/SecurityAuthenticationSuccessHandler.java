package com.bnk.plus.config.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 로그인시 User Auth 정보를 확인하여 각각의 권한에 맞는 URL로 redirect
 * 
 * @author tt.ks-choi
 * @date 20150213
 */
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger logger = LoggerFactory.getLogger(SecurityAuthenticationSuccessHandler.class);
	 
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }
 
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
 
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
 
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
 
    /** Builds the target URL according to the logic defined in the main class Javadoc. */
    protected String determineTargetUrl(Authentication authentication) {
        
    	/*
    	boolean isUser = false;
        boolean isAdmin = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals(AppConstBean.SECURITY_ROLE_AUTH_USER)) {
                isUser = true;
                break;
            } else if (grantedAuthority.getAuthority().equals(AppConstBean.SECURITY_ROLE_AUTH_ADMIN)) {
                isAdmin = true;
                break;
            }
        }
 
        if (isUser) {
            return AppConstBean.SECURITY_DEFAULT_SUCCESS_URL_TYPE_USER;
        } else if (isAdmin) {
            return AppConstBean.SECURITY_DEFAULT_SUCCESS_URL_TYPE_ADMIN;
        } else {
            throw new IllegalStateException();
        }
        */
    	return "";
    	
    }
 
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
 
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
