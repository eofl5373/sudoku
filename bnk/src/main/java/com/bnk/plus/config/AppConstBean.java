package com.bnk.plus.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/** **************************************************************************
 * Web Application의 Config 변수 저장소
 *
 * @author tt.ks-choi
 ************************************************************************** */
public class AppConstBean {

	/** **************************************************************************
	 * Common Value
	 ************************************************************************** */
	public static final String APP_CONFIG_PROPERTIES_PATH = "classpath:application.properties";		// Application Properties Path
	public static final String APP_CONFIG_PROPERTIES_PATH2 = "classpath:application2.properties";
	public static final String APP_CONFIG_VALIDATION_PROPERTIES = "classpath:messages/validation.properties";
	public static final String APP_COMPONENT_SCAN_PACKAGE = "com.bnk.plus";				// Component Scan Package (=Project Package)
	/*
	 * Tiles의 "pageTitle" attribute에 사용되며, "APP_NAME"은 AdviceController에서 @ModelAttribute("pageTitle")로 기본값으로 추가된다.
	 * 변경을 원할경우 Controller에서 model.addAttribute("pageTitle",StringValue); 에 입력된 값이 설정된다.
	 */
	public static final String APP_NAME = "BNK PLUS";


	/** **************************************************************************
	 * App Init
	 ************************************************************************** */
	public static final String 	APP_SERVLET_NAME = "dispatcher";										// 서블릿 명
	public static final String 	APP_SERVLET_MAPPING = "/";												// 서블릿 맵핑
	public static final String 	APP_MULTIPART_LOCATION = null;										// ?
	public static final long 	APP_MULTIPART_MAX_FILE_SIZE = 2000L * 1024L * 1024L;		// 서블릿 파일 업로드 사이즈
	public static final long 	APP_MULTIPART_MAX_REQUEST_SIZE = 2000L * 1024L * 1024L;		// ?
	public static final int 		APP_MULTIPART_FILE_SIZE_THRES_HOLE = 1000000;			// ?
	public static final int 		APP_SESSION_TIMEOUT = 180 * 60;											// 세션 타임 아웃 (Sec : 20*60 = 20분)


	/** **************************************************************************
	 * App Config
	 ************************************************************************** */
	public static final String 	APP_ENCODING = "UTF-8";													// Web Application 인코딩


	/** **************************************************************************
	 * Web Config
	 ************************************************************************** */
	public static final String 	MESSAGE_SOURCE_DEFAULT_LOCALE = "ko";							// 메시지 소스 디폴트 인코딩
	public static final String 	MESSAGE_SOURCE_DEFAULT_LOCALE_PARAM_NAME = "lang";				// 메시지 소스 변경 파라미터 키
	public static final String 	RESOURCE_HANDLER = "/**";										// 리소스 URL 맵핑 Path
	public static final String 	RESOURCE_LOCATIONS = "/WEB-INF/resources/";						// 리소스 물리적 Path
	//public static final String 	RESOURCE_LOCATIONS = "classpath:/com/gmk/swork/web/**";					// 리소스 물리적 Path


	public static final String 	VIEW_PREFIX = "/WEB-INF/views/";								// jsp Root Path
	public static final String 	VIEW_SUFFIX = ".jsp";											// jsp file suffix

	/** **************************************************************************
	 * Security Config
	 ************************************************************************** */
	public static final int SECURITY_MAXIMUM_SESSIONS = 200; // 세션 최대치
	public static final String SECURITY_ROLE_PREFIX = "";
	public static final String SECURITY_ROLE_DEFAULT = "ROLE_USER"; // 사용자 추가시 T2_AUTHORITIES에 기본으로 들어가는 역할
	public static final String SECURITY_ROLE_DEALER = "ROLE_DEALER"; // 사용자 추가시 T2_AUTHORITIES에 기본으로 들어가는 역할

	public static final String SECURITY_LOGIN_PAGE = "/product/co/login";
    public static final String SECURITY_LOGIN_PROCESSING_URL = "/session/front/login-proc";
    public static final String SECURITY_FAILURE_URL = "/session/front/login?c=fail";
    public static final String SECURITY_LOGOUT_URL = "/session/front/logout-proc";
    public static final String SECURITY_LOGOUT_SUCCESS_URL = "/front/index";

	public static final String SECURITY_USERNAME_PARAMETER = "un";
	public static final String SECURITY_PASSWORD_PARAMETER = "up";


	/**
	 *  <SECURITY_DEFAULT_SUCCESS_URL을 사용할지 안할지 결정>
	 *
	 *  핸들러를 사용하여
	 *  	SECURITY_DEFAULT_SUCCESS_URL_TYPE_USER 나
	 *  	SECURITY_DEFAULT_SUCCESS_URL_TYPE_ADMIN
	 *  로 권한을 구분하여 Login Success시의 이동 URL이 다르다면 False로 설정해야한다.
	 */
	public static final boolean SECURITY_DEFAULT_SUCCESS_URL_TYPE_USE = false;
	/**
	 * @title 로그인 성공 후 이동되는 URL
	 * @description
	 * {@link WebAppSecurityConfig}에서 CustomAuthenticationSuccessHandler를 사용할 경우 determineTargetUrl()를 통해 URL이 결정되며,
	 * 그렇지 않을 경우 (SuccessHandler를 쓰지 않을 경우)
	 * {@link SessionController}에서 @RequestMapping으로 아래 URL이 등록되어 있으며 이 메소드에서 Redirect 시킨다.
	 */

	public static final String SECURITY_DEFAULT_SUCCESS_URL = "/index";

	public static final String SECURITY_ADMIN_DEFAULT_SUCCESS_URL = "/admin/employeeMgmt/employee/list";
	public static final String SECURITY_ADMIN_ACCESS_DENIED_PAGE = "/admin/error/403";

	public static final String SECURITY_FRONT_DEFAULT_SUCCESS_URL = "/front/main/main";
	public static final String SECURITY_FRONT_ACCESS_DENIED_PAGE = "/front/error/403";

	/*
	 * Database Role을 사용 할 경우 정의되지 않은 URL에 대해 접근 허용에 대한 구분 설정
	 *   - true = 허용하지 않음 (로그인을 하였더라도 정의되지 않은 URL 패턴에 대해서는 역시 접근 불가)
	 *   - false = 허용함
	 */
	public static final boolean SECURITY_DEFEND_ALL_REQUEST_BY_NOT_MATCHING_URL_PATTERN = false;

	public static final String[] SECURITY_IGNORING_URL_PATTERN = {
			"/**/js/**"
			, "/**/css/**"
			, "/**/images/**"
		};

	/**
	 * <pre>
	 * org.springframework.security.crypto.password.StandardPasswordEncoder 로 변경되었으며, 더 이상 사용되지 않는다.
	 *
	 * 변경된 클래스는 스프링에서 제안하는 암호화 클래스이며, 스프링 시큐리티 3.1 이상 버전부터 사용 할 수 있다.
	 * 암호화 및 값 비교 시
	 * <code>
	 * | @Autowired StandardPasswordEncoder passwordEncoder;
	 * | method(String password){
	 * |   //암호화
	 * |   String encPassword = passwordEncoder.encode(password);
	 * |   // 값 비교
	 * |   boolean isMatched = passwordEncoder.matches(password, "암호화 된 password");
	 * | }
	 * </code>
	 * 와 같은 형태로 사용 할 수 있다.
	 * </pre>
	 * @author ks-choi
	 */
	public static final String StandardPasswordEncoderSalt = APP_COMPONENT_SCAN_PACKAGE;

	public static final String SECURITY_USERS_BY_USERNAME_QUERY =
			"SELECT user_id username , password , enabled FROM T2_USERS WHERE user_id=? AND use_yn = 'Y'";
	public static final String SECURITY_AUTHORITIES_BY_USERNAME_QUERY =
			"SELECT u.user_id username, a.authority authority FROM T2_USERS u, T2_AUTHORITIES a WHERE u.user_id = a.user_id AND u.user_id=? ";


	/** **************************************************************************
	 * DB - MyBatis Config
	 ************************************************************************** */
	public static final String MYBATIS_CONFIG_PATH = "classpath:com/bnk/plus/config/mybatis-config.xml";
	public static final String MYBATIS_TYPE_ALIASES_PACKAGE = APP_COMPONENT_SCAN_PACKAGE+".entity";
	//public static final String MYBATIS_MAPPER_SCAN_PACKAGE = APP_COMPONENT_SCAN_PACKAGE+".**.persistence";
	public static final String MYBATIS_MAPPER_SCAN_PACKAGE = APP_COMPONENT_SCAN_PACKAGE+".**.**.persistence";


	/** **************************************************************************
	 * Push Config
	 ************************************************************************** */
	public static final String PUSH_APP_TYPE = "ThinkTree";

	/** **************************************************************************
	 * Tiles Config
	 ************************************************************************** */
	public static final String[] TILES_LAYOUT_XML_PATH_PATTERN = {
		"/WEB-INF/views/template/layout/project/tiles.xml"		//PC tiles.xml
	};


	/** **************************************************************************
	 * Spring SMTP Mail Service | 2015.9.24 ks-choi
	 ************************************************************************** */
	public static final String MAIL_SERVICE_HOST = "smtp.gmail.com";
	public static final int MAIL_SERVICE_PORT = 587;
	public static final String MAIL_SERVICE_ENCODING = "UTF-8";
	public static final String MAIL_SERVICE_USERNAME = "jymailtest@gmail.com";	// test
	public static final String MAIL_SERVICE_PASSWORD = "fFT48tYMLidd0ta+E0eIyg==";

	/* Gmail Properties */
	public static final Properties MAIL_SERVICE_GMAIL_PROP = new Properties(){
		private static final long serialVersionUID = 1L;
		{
			setProperty("mail.smtp.auth", "true");
			setProperty("mail.smtp.starttls.enable", "true");
			setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
		}};


	/** **************************************************************************
	 * Error Page Map - 404페이지는 Web.xml에 등록 (=WAS가 발생시키는 예외로 WEB.xml에서만 맵핑 가능)
	 ************************************************************************** */
	public static final String ERROR_PAGES_DEFAULT = "/error/error";
	public static final String ERROR_PAGE_NOT_FOUND = "/error/404";
	@SuppressWarnings("serial")
	public static final Map<Object, Object> ERROR_PAGES_MAPPING_TO_EXCEPTION = new HashMap<Object, Object>(){
		{
			// put("Exception Class - String Type", "Error Page - Jsp Type");
			put("java.lang.NullPointerException", "/error/null");
		}
	};
}
