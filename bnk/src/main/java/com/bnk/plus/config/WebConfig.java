package com.bnk.plus.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import com.bnk.plus.config.interceptors.DetectAdminUserInterceptor;
import com.bnk.plus.config.interceptors.DetectFrontUserInterceptor;
import com.bnk.plus.config.viewresolver.JsonViewResolver;

/**
 * servlet-context.xml 의 대체
 * @author Administrator
 *
 */
@Configuration
@EnableWebMvc
//@Import(DatabaseConfig.class)		// Data Source Import
@ComponentScan(value=AppConstBean.APP_COMPONENT_SCAN_PACKAGE)
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler(AppConstBean.RESOURCE_HANDLER).addResourceLocations(AppConstBean.RESOURCE_LOCATIONS);
    }
	
//	// Validator - BeanValidation
//    @Override
//    public Validator getValidator() {
//        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
//        return validator;
//    }
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true)
				.useJaf(false)
				.ignoreAcceptHeader(true)
				.mediaType("html", MediaType.TEXT_HTML)
				//.mediaType("json", MediaType.APPLICATION_JSON)
				.defaultContentType(MediaType.TEXT_HTML);
	}
	
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		/*
		 * Controller의 @RequestMapping에서 
		 * "{pathA}/{pathB}"로 설정 후 
		 * @PathVariable("pathB") final String pathB
		 * 로 값 받으려할때 확장자는 넘어오지 않는다.
		 * UseSuffixPatternMatch를 false로 설정하면 확장자도 같이 넘어온다.
		 */
		configurer.setUseSuffixPatternMatch(false); 
	}
	
	@Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
         
        List<ViewResolver> resolvers = new ArrayList< ViewResolver >();
        
        // Excel File Download View Resolver
//        resolvers.add(excelViewResolver());
        
        // URL Base View Resolver
        UrlBasedViewResolver viewResolver0 = new UrlBasedViewResolver();
        viewResolver0.setViewClass(TilesView.class);
        viewResolver0.setOrder(0);
        resolvers.add(viewResolver0);
        
        resolvers.add(jsonViewResolver());
        
        // Internal Resource View Resolver
        InternalResourceViewResolver viewResolver1 = new InternalResourceViewResolver();
        viewResolver1.setOrder(1);
        viewResolver1.setViewClass(JstlView.class);
        viewResolver1.setPrefix(AppConstBean.VIEW_PREFIX);
        viewResolver1.setSuffix(AppConstBean.VIEW_SUFFIX);
        resolvers.add(viewResolver1);
        
         /*
        JsonViewResolver viewResolver2 = new JsonViewResolver();
        resolvers.add(viewResolver2);
         */
        
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setViewResolvers(resolvers);
        resolver.setContentNegotiationManager(manager);
        return resolver;
    }
	
	@Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(AppConstBean.TILES_LAYOUT_XML_PATH_PATTERN);
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }
	
//	/**
//     * View resolver for returning JSON in a view-based system. Always returns a
//     * {@link MappingJacksonJsonView}.
//     */
//    public class JsonViewResolver implements ViewResolver {
//        public View resolveViewName(String viewName, Locale locale) throws Exception {
//                MappingJackson2JsonView view = new MappingJackson2JsonView();
//                view.setPrettyPrint(true);
//                return view;
//        }
//    }
	
//	@Bean // 2016.4.19. ks-choi. ExcelDownloadView.java 주석의 예시처럼 변경하여 리졸버 등록 제거
//	public ViewResolver excelViewResolver(){
//		return new ExcelViewResolver();
//	}
	
	/**
	 * <pre>
	 * 1. 설명 : 다국어 지원을 위한 messageSource
	 * 2. 동작 : 
	 *    사용 방법
	 *     JAVA
	 *      @Autowired MessageSource;
	 *      messageSource.getMessage("code", {"args"}, "defaultMessage", Locale);
	 *     JSP (Taglib)
	 *      <spring:message code="code" text="defaultMessage"/>
	 * 3. Input : 
	 * 4. Output : 
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2016. 4. 8.     ks-choi                                      최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @return
	 */
	@Bean
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(
				"classpath:messages/message"
				, "classpath:messages/validation");
		// if true, the key of the message will be displayed if the key is not
		// found, instead of throwing a NoSuchMessageException
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setFallbackToSystemLocale(true);
		messageSource.setDefaultEncoding("UTF-8");
		// # -1 : never reload, 0 always reload
		messageSource.setCacheSeconds(60);
		return messageSource;
	}
	
	/**
	 * <pre>
	 * 1. 설명 : 다국어 설정을 위한 리졸버
	 * 2. 동작 : 
	 *     리졸버에는 SessionLocaleResolver, AcceptHeaderLocaleResolver, CookieLocaleResolver, FixedLocaleResolver 4가지가 있다.
	 *     > SessionLocaleResolver
	 *      세션 정보에서 Locale을 사용하고 LocaleChangeInterceptor를 추가로 등록하여 파라미터로 설정할 수 있다.
	 *      세션 설정 정보는 RequestContextUtils.getLocale(httpRequest)로 확인할 수 있다.
	 *     > AcceptHeaderLocaleResolver
	 *      request의 해더 정보 중 "accept-language" 값을 사용한다.
	 *     > CookieLocaleResolver
	 *      쿠키를 사용하며 setLocale()시 쿠키를 생성, resolveLocale()은 쿠키의 Locale을 가져온다.
	 *      값이 없을 경우 defaultLocale을 사용하고, defaultLocale이 없을 경우 해더의 "Accept-Language"를 사용한다.
	 *      Property : "cookieName, cookieDomain, cookiePath, cookieMaxAge, cookieSecure"
	 *     > FixedLocaleResolver
	 *      요청에 상관없이 특정 Locale(defaultLocale)을 설정하며, setLocale()을 지원하지 않는다.
	 * 3. Input : 
	 * 4. Output : 
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2016. 4. 8.     ks-choi                                      최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @return
	 */
	@Bean
	public LocaleResolver localeResolver() {
		
		// 설정 타입 1. 세션 정보에서 Locale 사용
		//SessionLocaleResolver sessionlocaleresolver = new SessionLocaleResolver();
		//sessionlocaleresolver.setDefaultLocale(StringUtils.parseLocaleString(AppConstBean.MESSAGE_SOURCE_DEFAULT_LOCALE));	// 기본 언어셋팅
		
		// 설정 타입 2. request의 Header의 "accept-language" 사용
		//AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
		
		// 설정 타입 3. 쿠키에 설정되어있는 Locale 사용
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		
		return cookieLocaleResolver;
	}
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		// request로 넘어오는 language parameter를 받아서 locale로 설정 한다.
		localeChangeInterceptor.setParamName(AppConstBean.MESSAGE_SOURCE_DEFAULT_LOCALE_PARAM_NAME);
		return localeChangeInterceptor;
	}
	
	/**
	 * 인터셉터 추가
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// Interceptor를 추가 한다.
		registry.addInterceptor(localeChangeInterceptor());
		
		/* ROLE_ADMIN, ROLE_USER에 따른 접근 권한을 체크하기 위한 Interceptor */
		
		/*	
			패턴 맵핑 (모든 URL에 대해 인터셉터 적용시 삭제) 
			.addPathPatterns("")         : 패턴 추가
			.excludePathPatterns("")    : 패턴 제외
		*/
		
		
		registry.addInterceptor(new DetectFrontUserInterceptor())
			.addPathPatterns("/front/**");
//			.excludePathPatterns("/session/**")
//			.excludePathPatterns("/rest/**")
//			.excludePathPatterns("/excel/**")
//			.excludePathPatterns("/attach/**")
//			.excludePathPatterns("/error/**")
//			.excludePathPatterns("/setting/**")
//			.excludePathPatterns("/download/**")
//			.excludePathPatterns("/image/**")
			
//			.excludePathPatterns("/session/front/**")
//			.excludePathPatterns("/front/**");
		
		registry.addInterceptor(new DetectAdminUserInterceptor())
			.addPathPatterns("/product/**");
		
	}
	
	
//	/** 161212 jy-seo 에러 메시지가 뜨지않아 임시주석처리
//	 * Exception 별 Error Page Mapping
//	 * @return
//	 */
//	@Bean
//	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
//	    SimpleMappingExceptionResolver b = new SimpleMappingExceptionResolver();
//
//	    b.setOrder(1);
//	    b.setDefaultErrorView(AppConstBean.ERROR_PAGES_DEFAULT);
//
//	    Properties mappings = new Properties();
//	    mappings.putAll(AppConstBean.ERROR_PAGES_MAPPING_TO_EXCEPTION);;
//
//	    b.setExceptionMappings(mappings);
//	    return b;
//	}
	
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		/*
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(5 * 1024 * 1024);
		return multipartResolver;
		*/
		return new StandardServletMultipartResolver();
	}
	
	@Bean
	public CacheManager getEhCacheManager(){
	        return  new EhCacheCacheManager(getEhCacheFactory().getObject());
	}
	@Bean
	public EhCacheManagerFactoryBean getEhCacheFactory(){
		EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
		factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		factoryBean.setShared(true);
		return factoryBean;
	}

	@Bean
	public ViewResolver jsonViewResolver() {
		return new JsonViewResolver();
	}
}
