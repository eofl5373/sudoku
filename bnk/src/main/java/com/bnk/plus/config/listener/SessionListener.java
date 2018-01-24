package com.bnk.plus.config.listener;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.bnk.plus.config.AppConstBean;

public class SessionListener extends HttpSessionEventPublisher {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);
	
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().setMaxInactiveInterval(AppConstBean.APP_SESSION_TIMEOUT);
		logger.debug("Session ID : ".concat(se.getSession().getId()).concat(" created at ").concat(new Date().toString()));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		logger.debug("Session ID : ".concat(se.getSession().getId()).concat(" destroyed at ").concat(new Date().toString()));
	}

}