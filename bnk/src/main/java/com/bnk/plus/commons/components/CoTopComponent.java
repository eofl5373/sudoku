package com.bnk.plus.commons.components;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bnk.plus.commons.CoCommonFunc;
import com.bnk.plus.commons.components.bean.ComBean;
import com.bnk.plus.commons.util.DateUtil;
import com.bnk.plus.commons.util.StringUtil;
import com.bnk.plus.config.AppConstBean;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import atg.taglib.json.util.JSONObject;


// TODO: Auto-generated Javadoc
/**
 * 각 Controller 의 상위 Component 클래스.
 *
 * @author sw-yun
 */
@Component
@PropertySources(value = {@PropertySource(value=AppConstBean.APP_CONFIG_PROPERTIES_PATH)})
public class CoTopComponent {

	protected final String tilesAjaxPrefix = "tiles.ajax.product.";

	/**  Spring Security Password Encoder. */
	@Autowired protected static StandardPasswordEncoder passwordEncoder;

	/**  WebApplicationContext. */
	protected static WebApplicationContext applicationContext;

	/**
	 * Sets the web application context.
	 *
	 * @param applicationContext the new web application context
	 */
	@SuppressWarnings("static-access")
	@Autowired
	public void setWebApplicationContext(WebApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/** The message source. */
	protected static MessageSource messageSource;

	/**
	 * Sets the message source.
	 *
	 * @param messageSource the new message source
	 */
	@SuppressWarnings("static-access")
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/** The env. */
	protected static Environment appEnv;

	/**
	 * Sets the environment.
	 *
	 * @param env the new environment
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("static-access")
	@Resource
	public void setEnvironment(Environment env) throws IOException {
		this.appEnv = env;
		setEnvDefaultData();
	}

	/**
	 * Sets the env default data.
	 */
	private void setEnvDefaultData() {
		isLiveMode = "LIVE".equalsIgnoreCase(appEnv.getProperty("server.mode"));
		isStageMode = "STAGE".equalsIgnoreCase(appEnv.getProperty("server.mode"));
		isTestMode = "DEV".equalsIgnoreCase(appEnv.getProperty("server.mode"));
	}

	/** The Constant log_usual. */
	protected static final Logger log_usual = LoggerFactory.getLogger("ADMIN-USUAL");

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger("DEFAULT");

	/** The Constant log_update. */
	protected static final Logger log_update = LoggerFactory.getLogger("ADMIN-UPDATE");

	/** The Constant log_error. */
	protected static final Logger log_error = LoggerFactory.getLogger("ADMIN-ERROR");

	/** The Constant mlog_usual. */
	protected static final Logger mlog_usual = LoggerFactory.getLogger("MOBILE-USUAL");

	/** The Constant mlog_update. */
	protected static final Logger mlog_update = LoggerFactory.getLogger("MOBILE-UPDATE");

	/** The Constant mlog_error. */
	protected static final Logger mlog_error = LoggerFactory.getLogger("MOBILE-ERROR");

	protected static final Logger schLog = LoggerFactory.getLogger("SYSTEM-SCHEDULE");


    /**
     * <pre>
     * 1. 설명 : Null 혹은 빈값을 체크한다.
     * 2. 동작 : Null이나 빈값이면 true, 아니라면 false를 리턴
     * 3. Input : String
     * 4. Output :
     *            true = Null or Empty
     *            false = Not Empty
     * 5. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2015. 8. 21.     ks-choi         최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @param s the s
     * @return boolean
     */
    public static Boolean isEmpty(String s) {
		return StringUtil.isEmptyTrimmed(s);
	}

    /**
     * Checks if is null object.
     *
     * @param o the o
     * @return the boolean
     */
    public static Boolean isNullObject(Object o) {
    	if (o instanceof String){
    		return StringUtil.isEmptyTrimmed((String)o);
    	} else {
    		return o == null;
    	}
	}

    /**
     * <pre>
     * 1. 설명 : 문자열이 <code>null</code>이면 공백를 반환하고 아니면 <code>str</code>을 반환한다.
     * 2. 동작 :
     * 3. Input : String 문자열
     * 4. Output :
     *             입력문자열이 null일경우 공백
     *             아닐 경우 확인요청 문자열 반환
     * 5. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2015. 8. 21.     ks-choi         최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @param s the s
     * @return String
     */
    public static String nvl(String s)
    {
        return StringUtil.defaultString(s);
    }


    /**
     * <pre>
     * 1. 설명 : 문자열이 <code>null</code>이면 <code>defaultString</code>를 반환하고 아니면 <code>str</code>을 반환한다.
     * 2. 동작 : 문자열이 null or trimmed Empty라면 defaultString을 리턴하여 아니면 문자열을 다시 반환
     * 3. Input :
     *             String checkedString (확인 요청 문자열)
     *             String defaultString (값이 비었을 때 변경할 문자열)
     * 4. Output : 처리 완료된 문자열
     * 5. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2015. 8. 21.     ks-choi         최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @param checkedString the checked string
     * @param defaultString the default string
     * @return String completeString
     */
    public static String nvl(String checkedString, String defaultString)
    {
        return StringUtil.isEmptyTrimmed(checkedString) ? defaultString : checkedString;
    }

    /**
     * <pre>
     * 1. 설명 : 2진 비트 연산(And)을 통해 메뉴나 체크박스 등의 선택 여부를 간략히 확인할 수 있다.
     * 2. 동작 : A, B 두 값의 Bit 연산(And)을 수행하여 Boolean형 결과를 리턴한다.
     * 	         ex 1) 15 (1111) & 8 (1000) = 8 (1000) : true
     *          ex 2) 11 (0111) & 2 (0010) = 8 (0010) : true
     *          ex 3) 4 (0100) & 8 (1000) = 0 (0000) : false
     *          ex 4) jsp taglib
     *              <c:choose>
     *                  <c:when test="${ct:bitOperAnd(5,4)}">true</c:when>
     *                  <c:otherwise>false</c:otherwise>
     *              </c:choose>
     * 3. Input : int A, int B
     * 4. Output : boolean(true, false)
     * 5. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2015. 8. 17.     ks-choi         최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @param a the a
     * @param b the b
     * @return boolean (true: 가능, false: 불가능)
     */
    public static boolean bitOperAnd(int a, int b) {
		return (a & b) > 0;
	}


    /**
     *
     * <pre>
     * 1. 설명 : 입력된 일자와 마감 기간으로 오늘 날짜가 마감 기간인지 확인한다.
     * 2. 동작 : 입력된 일자부터 오늘까지의 기간을 계산하고,
     * 			마감 기간과 비교하여 마감 여부를 출력한다.
     * 3. Input : date: 일자, period: 마감 기간
     * 			ex) String date = "yyyy-MM-dd"(default format)
     * 4. Output : boolean: 마감 여부
     * 5. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2015. 9. 8.     JySeo         최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @param date the date
     * @param period the period
     * @return boolean
     */
    public static boolean isLastPeriod(String date, int period){
    	return DateUtil.isLastPeriod(date, period);
    }

    /**
     * Checks if is last period.
     *
     * @param date the date
     * @param format the format
     * @param period the period
     * @return true, if is last period
     */
    public static boolean isLastPeriod(String date, String format, int period){
    	return DateUtil.isLastPeriod(date, format, period);
    }

    /**
     * Checks if is last period.
     *
     * @param date the date
     * @param period the period
     * @return true, if is last period
     */
    public static boolean isLastPeriod(Date date, int period){
    	return DateUtil.isLastPeriod(date, period);
    }

    /**
     * <pre>
     * 1. 설명 : 입력된 기간에 대한 현재 진행 상태를 정한다.
     * 2. 동작 : 시작일과 종료일을 입력받아 기간을 비교하여
     * 			오늘 날짜를 기준으로 진행 상태를 출력한다.
     * 3. Input : date1: 시작일, date2: 종료일
     * 			ex) String date = "yyyy-MM-dd"(default format)
     * 4. Output : int 진행 상태
     * 			ex) -1: 이전, 0: 진행, 1: 종료
     * 5. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2015. 9. 8.     JySeo         최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @param date1 the date1
     * @param date2 the date2
     * @return int
     */
    public static int getPeriodStatus(String date1, String date2){
    	return DateUtil.getPeriodStatus(date1, date2);
    }

    /**
     * Gets the period status.
     *
     * @param date1 the date1
     * @param date2 the date2
     * @param format the format
     * @return the period status
     */
    public static int getPeriodStatus(String date1, String date2, String format){
    	return DateUtil.getPeriodStatus(date1, date2, format);
    }

    /**
     * Gets the period status.
     *
     * @param date1 the date1
     * @param date2 the date2
     * @return the period status
     */
    public static int getPeriodStatus(Date date1, Date date2){
		return DateUtil.getPeriodStatus(date1, date2);
    }

    /**
     * 
     * <pre>
     * 1. 설명 : 로그인 User 아이디 확인
     * 2. 동작 : 로그인 User의 아이디를 리턴한다.
     *             로그인을 안했을 경우 "anonymousUser"가 리턴된다.
     * 3. Input :
     * 4. Output : String
     * 5. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2015. 8. 18.     ks-choi         최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @return String userName ({loginUserId} or "anonymousUser")
     */
    protected static String loginUserName() {
    	try{
    	return SecurityContextHolder.getContext().getAuthentication().getName();
    	}catch(Exception e) {return "";}
	}

    /**
     * <pre>
     * 1. 설명 : 로그인 유저의 권한 확인
     * 2. 동작 : 로그인 한 유저의 권한을 확인한다.
     *      AppConstBean.SECURITY_ROLE_PREFIX에 값이 정의되어 있을 경우 권한 명만 입력하면 된다.
     *      예) ROLE_ADMIN 을 확인 할 때 AppConstBean.SECURITY_ROLE_PREFIX값이 "ROLE_" 일 경우
     *             - hasRole(req, "ADMIN")
     *             을 호출하면 Boolean 데이터를 리턴한다.
     * 3. Input : HttpServletRequest, Role String
     * 4. Output :
     *             boolean ( true: 일치, false: 불일치 )
     * 5. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2015. 8. 19.     ks-choi         최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @param req the req
     * @param role the role
     * @return boolean (true: 권한 있음, false: 권한 없음)
     */
    protected static boolean hasRole(HttpServletRequest req, String role) {
    	SecurityContextHolderAwareRequestWrapper securityContextHolderAwareRequestWrapper = new SecurityContextHolderAwareRequestWrapper(req, AppConstBean.SECURITY_ROLE_PREFIX);
    	if (role.indexOf(AppConstBean.SECURITY_ROLE_PREFIX) == 0) role = role.replaceFirst(AppConstBean.SECURITY_ROLE_PREFIX, "");
    	return securityContextHolderAwareRequestWrapper.isUserInRole(role);
	}

    /**
     * <pre>
     * 1. 설명 : 로그인 한 User의 권한을 반환한다.
     * 2. 동작 : Security Context Holder에서 권한을 가져와 여러 Role이 맵핑되어 있어도
     *       처음 한개의 권한 값만 리턴한다.
     *       리턴시 {@link AppConstBean}.SECURITY_ROLE_PREFIX 값을 Replace한다.
     *       ex) ROLE_ADMIN > ADMIN
     * 3. Input :
     * 4. Output :
     * 5. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2015. 9. 3.     hk-cho         최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @return the string
     */
    protected static String loginUserRole() {
    	String result = "anonymousUser";
    	try{
	    	Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	    	if(!authorities.isEmpty()){
	    		for (GrantedAuthority authority : authorities) {
	    			result = (authority.getAuthority()).replaceFirst(AppConstBean.SECURITY_ROLE_PREFIX, "");
	    			break;
				}
	    	}
    	} catch(Exception e){}
    	return result;
	}

    /**
     * Checks if is login.
     *
     * @return true, if is login
     */
    protected static boolean isLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !"anonymousUser".equalsIgnoreCase(auth.getName()) && auth.isAuthenticated()) {
        	return true;
        }
    	return false;
    }

    /**
     * <pre>
     * 1. 설명 : org.springframework.security.crypto.password.StandardPasswordEncoder 로 String을 암호화한다.
     * 2. 동작 : 파라미터로 전달받은 String을 암호화하여 리턴
     * 3. Input :
     *             String rawPassword
     * 4. Output :
     *             String encodePassword
     * 5. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2015. 8. 21.     ks-choi         최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @param rawPassword the raw password
     * @return String encodePassword
     */
    protected static String encodePassword(String rawPassword) {
    	if (rawPassword == null || rawPassword.length() < 1) return null;
    	return passwordEncoder.encode(rawPassword);
	}

    /**
     * <pre>
     * 1. 설명 : 암호화 되지 않은 password와 암호화된 password를 비교한다.
     * 2. 동작 : 파라미터로 전달받은 암호화된 password, 암호화된 password를 비교하여 같으면 true. 다르면 false를 리턴한다.
     * 3. Input :
     *             String rawPassword
     *             String encodedPassword
     * 4. Output :
     *             boolean (true: 일치, false: 불일치)
     * 5. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2015. 8. 21.     ks-choi         최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @param rawPassword the raw password
     * @param encodedPassword the encoded password
     * @return boolean (true: 일치, false: 불일치)
     */
    protected static boolean  equalPassword(String rawPassword, String encodedPassword) {
    	if (rawPassword == null || rawPassword.length() < 1) return false;
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}


    /**
     * <pre>
     * 1. 설명 : 요청된 Full URI를 가져옵니다.
     * 2. 동작 : Full URI에서 현재페이지(curPage)파라미터를 삭제합니다.
     * 3. Input : HttpServletRequest
     * 4. Output :
     * 5. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2015. 9. 3.     hk-cho         최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @param request the request
     * @return the string
     */
    public static String queryStringWithoutCurPage(HttpServletRequest request) {
		StringBuffer requestURL = request.getRequestURL();
		String queryString = request.getQueryString();

		if (queryString == null) {
			return requestURL.append('?').toString();
		} else {
			if(StringUtil.contains(queryString, "curPage")){
				String queryForm = "&".concat("curPage").concat("=");
				String queryVal = request.getParameterValues("curPage")[0];
				queryString = StringUtil.remove(queryString, queryForm + queryVal);
			}
			return requestURL.append('?').append(queryString).toString();
		}
	}


    /**
     * <pre>
     * 1. 설명 : 요청된 URL과 queryString을 가져옵니다.
     * 2. 동작 :  http://${URL}?${param1=a&param2=b}
     * 3. Input :
     * 4. Output :
     * 5. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2015. 9. 9.     hj-kim								         최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @param request the request
     * @return the request url and query string
     */
    public static String getRequestURLAndQueryString(HttpServletRequest request) {

    	StringBuffer requestURL = request.getRequestURL();
		String queryString = request.getQueryString();

		return requestURL +"?"+ queryString;
    }


    /**
     * Gets the random time string.
     *
     * @return the random time string
     */
    public static String getRandomTimeString() {

    	return "";
    }


	/**
	 * Send json object.
	 *
	 * @param response the response
	 * @param json the json
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void sendJsonObject(HttpServletResponse response, JSONObject json) throws IOException {
		response.setCharacterEncoding(AppConstBean.APP_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	/**
	 * To json.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	protected static String toJson(Object obj) {
		Gson gson = CoCommonFunc.getGsonBuiler();
		return gson.toJson(obj);
	}

	/**
	 * From json.
	 *
	 * @param jsonString the json string
	 * @param collectionType the collection type
	 * @return the object
	 */
	protected static Object fromJson(String jsonString, Type collectionType) {
		Gson gson = CoCommonFunc.getGsonBuiler();
		return gson.fromJson(jsonString, collectionType);
	}
	/**
	 * From json.
	 *
	 * @param jsonString the json string
	 * @param collectionType the collection type
	 * @return the object
	 */
	protected static Map<String, Object> fromJsonToMap(String jsonString) {
		Gson gson = CoCommonFunc.getGsonBuiler();
		Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
		return (Map<String, Object>) fromJson(jsonString, mapType);
	}

	/**
	 * Make json response header.
	 *
	 * @param obj the obj
	 * @return the response entity
	 */
	protected static ResponseEntity<Object> makeJsonResponseHeader(Object obj) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");
		return new ResponseEntity<Object>(toJson(obj), responseHeaders, HttpStatus.OK);
	}

	/**
	 * Make json response header.
	 *
	 * @return the response entity
	 */
	protected static ResponseEntity<Object> makeJsonResponseHeader() {
		return makeJsonResponseHeader(true, null);
	}

	/**
	 * Make json response header.
	 *
	 * @param alertMsg the alert msg
	 * @return the response entity
	 */
	protected static ResponseEntity<Object> makeJsonResponseHeader(String alertMsg) {
		return makeJsonResponseHeader(true, alertMsg);
	}

	/**
	 * Make json response header.
	 *
	 * @param isValid the is valid
	 * @param alertMsg the alert msg
	 * @return the response entity
	 */
	protected static ResponseEntity<Object> makeJsonResponseHeader(boolean isValid, String alertMsg) {
		return makeJsonResponseHeader(isValid, alertMsg, null);
	}

	/**
	 * Make json response header.
	 *
	 * @param isValid the is valid
	 * @param alertMsg the alert msg
	 * @param obj the obj
	 * @return the response entity
	 */
	protected static ResponseEntity<Object> makeJsonResponseHeader(boolean isValid, String alertMsg, Object obj) {
		HttpHeaders responseHeaders = new HttpHeaders();
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("isValid", isValid ? "true" : "false");
		if(!isEmpty(alertMsg)) {
			resultMap.put("validMsg", alertMsg);
		}
		if(obj != null) {
			resultMap.put("resultData", obj);
		}
		responseHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");
		return new ResponseEntity<Object>(toJson(resultMap), responseHeaders, HttpStatus.OK);
	}

	/**
	 * Gets the grid pager map.
	 *
	 * @param vo the vo
	 * @return the grid pager map
	 */
	protected static Map<String, Object> getGridPagerMap(ComBean vo) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("page", vo.getPage());
		map.put("total", vo.getBlockEnd());
		map.put("records", vo.getTotListSize());
		return map;
	}

	/**
	 * 요청이 멀티파트 요청인지 확인합니다.
	 *
	 * @param req the req
	 * @return boolean
	 */
	protected boolean isMultiPartReq(HttpServletRequest req) {
		return req instanceof MultipartHttpServletRequest;
	}

	/**
	 * <pre>
	 * 1. 설명 : 텍스트에 포함된 HTML 태그를 제거합니다.
	 * 2. 동작 : 정규식을 이용하여 content에 포함되어 있는 HTML 태그를 제거합니다.
	 * 3. Input : String content
	 * 4. Output : String content
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2016. 8. 8.     jw-ahn         최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @param content the content
	 * @return the text from html text
	 */
	public static String getTextFromHtmlText(String content) {
		Pattern SCRIPTS = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>",Pattern.DOTALL);
		Pattern STYLE = Pattern.compile("<style[^>]*>.*</style>",Pattern.DOTALL);
		Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
		Pattern nTAGS = Pattern.compile("<\\w+\\s+[^<]*\\s*>");
		Pattern ENTITY_REFS = Pattern.compile("&[^;]+;");
		Pattern WHITESPACE = Pattern.compile("\\s\\s+");

		Matcher m;

		m = SCRIPTS.matcher(content);
		content = m.replaceAll("");
		m = STYLE.matcher(content);
		content = m.replaceAll("");
		m = TAGS.matcher(content);
		content = m.replaceAll("");
		m = nTAGS.matcher(content);
		content = m.replaceAll("");
		m = ENTITY_REFS.matcher(content);
		content = m.replaceAll("");
		m = WHITESPACE.matcher(content);
		content = m.replaceAll(" ");

		return content;
	}

	/**
	 * 현재 설정중인 locale정보를 반환.
	 *
	 * @return the locale
	 */
	protected static Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}

	/**
	 * Gets the message.
	 *
	 * @param code the code
	 * @return the message
	 */
	protected static String getMessage(String code) {
		return messageSource.getMessage(code, null, getLocale());
	}

	/**
	 * Gets the message.
	 *
	 * @param code the code
	 * @param args the args
	 * @return the message
	 */
	protected static String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, null, getLocale());
	}

	/**
	 * Gets the webapp context.
	 *
	 * @return the webapp context
	 */
	protected static WebApplicationContext getWebappContext() {
		/*
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		// HttpSession 객체 가져오기
		HttpSession session = request.getSession();

		// ServletContext 객체 가져오기
		ServletContext conext = session.getServletContext();

		// Spring Context 가져오기
		return WebApplicationContextUtils.getWebApplicationContext(conext);

		// 스프링 빈 가져오기 & casting
		//SampleBean sBean = (SampleBean) wContext.getBean("sampleBean");

		 */

		return applicationContext;
	}

	/**
	 * Excel to response entity.
	 *
	 * @param excelPath the excel path
	 * @param downFileName the down file name
	 * @return the response entity
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @용도 엑셀파일을 리스폰스 엔티티로 감싸준다.
	 */
	public static ResponseEntity<FileSystemResource> excelToResponseEntity(String excelPath ,String downFileName) throws IOException{

		String fullLogiPath = null;
		fullLogiPath = excelPath;
		ResponseEntity<FileSystemResource> responseEntity = null;

		java.io.File file = new java.io.File(fullLogiPath);
	    FileSystemResource fileSystemResource = new FileSystemResource(file);

	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downFileName+".xlsx");

	    responseEntity = new ResponseEntity<FileSystemResource>(fileSystemResource, responseHeaders, HttpStatus.OK);

	    responseHeaders.add(HttpHeaders.CONTENT_LENGTH, Long.toString(fileSystemResource.contentLength()));

	    return responseEntity;
	}

	/**
	 * Avoid null.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String avoidNull(String s) {
		return avoidNull(s, "");
	}

	/**
	 * Avoid null.
	 *
	 * @param s the s
	 * @param rep the rep
	 * @return the string
	 */
	public static String avoidNull(String s, String rep) {
		return isEmpty(s) ? rep : s;
	}

	/**
	 * Gets the string from file.
	 *
	 * @param path the path
	 * @return the string from file
	 * @throws Exception the exception
	 */
	public static String getStringFromFile(String path) throws Exception{
		FileInputStream in = new FileInputStream(path);
		byte[] buf = new byte[in.available()];
		while(in.read(buf) != -1){}
		in.close();
		return new String(buf);
	}

	/** The is live mode. */
	public static boolean isLiveMode = false;

	/** The is stage mode. */
	public static boolean isStageMode = false;

	/** The is test mode. */
	public static boolean isTestMode = true;

	/**
	 * Put session object.
	 *
	 * @param key the key
	 * @param obj the obj
	 * @return true, if successful
	 */
	public static boolean putSessionObject(String key, Object obj) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		if(request != null) {
			HttpSession session = request.getSession();
			if(session != null) {
				session.setAttribute(key, obj);
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the session object.
	 *
	 * @param key the key
	 * @return the session object
	 */
	public static Object getSessionObject(String key) {
		return getSessionObject(key, false);
	}

	/**
	 * Gets the session object.
	 *
	 * @param key the key
	 * @param oneTimeFlag the one time flag
	 * @return the session object
	 */
	public static Object getSessionObject(String key, boolean oneTimeFlag) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		if(request != null) {

			HttpSession session = request.getSession();
			if(session != null) {
				Object sessionObj = session.getAttribute(key);
				if(oneTimeFlag) {
					try {
						session.removeAttribute(key);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
				return sessionObj;
			}
		}
		return null;
	}

	/**
	 * Delete session.
	 *
	 * @param key the key
	 */
	public static void deleteSession(String key) {

		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			if(request != null) {

				HttpSession session = request.getSession();
				if(session != null) {
					session.removeAttribute(key);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
