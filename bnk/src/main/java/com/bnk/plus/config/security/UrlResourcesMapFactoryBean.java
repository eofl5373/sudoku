package com.bnk.plus.config.security;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class UrlResourcesMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>> {
	
	private SecuredObjectService securedObjectService;
	
	private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap;
	
	public void init(SecuredObjectService securedObjectService) throws Exception {
		this.securedObjectService = securedObjectService;
		requestMap = this.securedObjectService.getRolesAndUrl();
	}
	
	@Override
	public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject() throws Exception {
		if(requestMap == null){
			requestMap = securedObjectService.getRolesAndUrl();
		}
		return requestMap;
	}

	@Override
	public Class<?> getObjectType() {
		return LinkedHashMap.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
