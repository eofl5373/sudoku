package com.bnk.plus.config;

import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.bnk.plus.config.listener.SessionListener;

/**
 * web.xml 대체
 * 
 * @author tt.ks-choi
 *
 */
public class WebAppIntializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext)throws ServletException {
		
		// Spring MVC
		final AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		mvcContext.register(WebConfig.class);

		/**
		 * Listener
		 */
		servletContext.addListener(new ContextLoaderListener(mvcContext));
		servletContext.addListener(new SessionListener());
		
		/**
		 * Filters
		 */
		// Method Filter
		final FilterRegistration.Dynamic hiddenHttpMethodFilter = servletContext.addFilter("httpMethodFilter", new HiddenHttpMethodFilter());
		hiddenHttpMethodFilter.addMappingForUrlPatterns(null, true, "/*");
		// Method Filter의 메소드 중 Put과 Delete는 Multipart 요청은 맵핑되지 않는다.(POST로 맵핑됨) 이를 맵핑시켜주기 위해 아래의 필터를 추가
		final FilterRegistration.Dynamic multipartFilter = servletContext.addFilter("multipartFilter", new MultipartFilter());
		multipartFilter.setInitParameter("multipartResolverBeanName", "multipartResolver");
		multipartFilter.addMappingForServletNames(null, true, "/*");
		
		// Encoding Filter
        final FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
        characterEncodingFilter.setInitParameter("encoding", AppConstBean.APP_ENCODING);
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
        
        // Security Filter
        /*final FilterRegistration.Dynamic springSecurityFilter = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"));
        springSecurityFilter.addMappingForUrlPatterns(null, false, "/*");
        springSecurityFilter.setAsyncSupported(true);*/
        
        
        
        
        
        // DispatcherServlet
        final ServletRegistration.Dynamic appServlet = servletContext.addServlet(AppConstBean.APP_SERVLET_NAME, new DispatcherServlet(mvcContext));
//        appServlet.setAsyncSupported(true);
        
        appServlet.setMultipartConfig(new MultipartConfigElement(
        		AppConstBean.APP_MULTIPART_LOCATION
        		, AppConstBean.APP_MULTIPART_MAX_FILE_SIZE
        		, AppConstBean.APP_MULTIPART_MAX_REQUEST_SIZE
        		, AppConstBean.APP_MULTIPART_FILE_SIZE_THRES_HOLE));
        
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping(AppConstBean.APP_SERVLET_MAPPING);
        
	}
}
