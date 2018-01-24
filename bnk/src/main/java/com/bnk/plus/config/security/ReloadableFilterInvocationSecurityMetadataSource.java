package com.bnk.plus.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.bnk.plus.config.AppConstBean;

public class ReloadableFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private static final String ROLE_CHECK_NOT_MATCHING_URL_PATTERN_TAG = "role_check_not_matching_url_pattern_tag";
	
	// > Database에서 Resource 접근 규약을 다시 Load 하는 법
	//   @Autowired FilterSecurityInterceptor filterSecurityInterceptor;
	//   ReloadableFilterInvocationSecurityMetadataSource reloadableFilterInvocationSecurityMetadataSource = (ReloadableFilterInvocationSecurityMetadataSource)filterSecurityInterceptor.getSecurityMetadataSource();
	//   reloadableFilterInvocationSecurityMetadataSource.reload();
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final Map<RequestMatcher, List<ConfigAttribute>> requestMap;
	
	@Autowired private SecuredObjectService securedObjectService;
	
	public ReloadableFilterInvocationSecurityMetadataSource(LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap) {
		this.requestMap = requestMap;
	}
	
	public void setSecuredObjectService(SecuredObjectService securedObjectService){
		this.securedObjectService = securedObjectService;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation)object).getRequest();
		Collection<ConfigAttribute> result = null;
		/**
		 * 요청 URL Resource의 접근 Role이 설정 된 경우 해당 Role Name을 리턴하여 체크 될 수 있도록 한다.
		 */
		for(Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()){
			if(entry.getKey().matches(request)){
				result = entry.getValue();
				break;
			}
		}
		
		/**
		 * URL 패턴 제어 시 설정되지 않은 URL 패턴에 대해 허용 할 것인지에 대한 설정을 한다.
		 * 
		 * 구동 방식은 등록되지 않은 URL의 경우 하드코딩된 임의의 Role을 강제 등록하여 접근할 수 없도록 한다.
		 * 단, 로그인이 되어 있는 사용자인지는 구분하지 않는다. (즉, 로그인이 되어 있더라도 URL 패턴에 정의되지 않은 resource에는 접근할 수 없다.)
		 * 
		 * *Login Page는 제외한다.
		 */
		if(AppConstBean.SECURITY_DEFEND_ALL_REQUEST_BY_NOT_MATCHING_URL_PATTERN){
			if(result == null && !(new AntPathRequestMatcher(AppConstBean.SECURITY_LOGIN_PAGE).matches(request))){
				result = new ArrayList<ConfigAttribute>();
				result.add(new SecurityConfig(ROLE_CHECK_NOT_MATCHING_URL_PATTERN_TAG));
			}
		}
		return result;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
		for(Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()){
			allAttributes.addAll(entry.getValue());
		}
		return allAttributes;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	public void reload() throws Exception{
		LinkedHashMap<RequestMatcher, List<ConfigAttribute>> reloadedMap = securedObjectService.getRolesAndUrl();
		Iterator<Entry<RequestMatcher, List<ConfigAttribute>>> iterator = reloadedMap.entrySet().iterator();
		
		requestMap.clear();
		
		while (iterator.hasNext()) {
			Entry<RequestMatcher, List<ConfigAttribute>> entry = iterator.next();
			requestMap.put(entry.getKey(), entry.getValue());
		}
		
		if(logger.isInfoEnabled())logger.info("Secured Url Resources - Role Mappings Reloaded At Runtime!! ");
	}
}
