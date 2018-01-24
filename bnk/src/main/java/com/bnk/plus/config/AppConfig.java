package com.bnk.plus.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactory;

import com.bnk.plus.commons.util.CryptUtil;

/**
 * root-context.xml 의 대체
 * @author Administrator
 *
 */
@Configuration
@ComponentScan(value=AppConstBean.APP_COMPONENT_SCAN_PACKAGE)
public class AppConfig {
	
	/**
	 * <pre>
	 * 1. 설명 : Spring Mail Sender
	 * 2. 동작 : 
	 * 3. Input : 
	 * 4. Output : 
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2015. 9. 24.     ks-choi         최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @return {@link org.springframework.mail.MailSender} MailSender
	 */
	@Bean(name={"mailSender"})
	public JavaMailSenderImpl mailSender(){
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		try{
			mailSenderImpl.setHost(AppConstBean.MAIL_SERVICE_HOST);
			mailSenderImpl.setPort(AppConstBean.MAIL_SERVICE_PORT);
			mailSenderImpl.setUsername(AppConstBean.MAIL_SERVICE_USERNAME);
			mailSenderImpl.setPassword(CryptUtil.decryptAES256(AppConstBean.MAIL_SERVICE_PASSWORD, "a0f8enekjak32bFI"));
			mailSenderImpl.setDefaultEncoding(AppConstBean.MAIL_SERVICE_ENCODING);
			mailSenderImpl.setJavaMailProperties(AppConstBean.MAIL_SERVICE_GMAIL_PROP);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return mailSenderImpl;
	}
	
	/*
     * Velocity configuration.
     */
    @Bean
    public VelocityEngine getVelocityEngine() throws VelocityException, IOException {
        VelocityEngineFactory factory = new VelocityEngineFactory();
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
 
        factory.setVelocityProperties(props);
        return factory.createVelocityEngine();
    }

}