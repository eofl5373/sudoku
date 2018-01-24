package com.bnk.plus.config.security;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

@Service("securedObjectService")
public class SecuredObjectServiceImpl implements SecuredObjectService {
	
	@Autowired private SecuredObjectDao securedObjectDao;
	public SecuredObjectDao getSecuredObjectDao(){
		return securedObjectDao;
	}
	public void setSecuredObjectDao(SecuredObjectDao securedObjectDao){
		this.securedObjectDao = securedObjectDao;
	}

	@Override
	public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getRolesAndUrl() throws Exception {
		LinkedHashMap<RequestMatcher, List<ConfigAttribute>> ret = new LinkedHashMap<RequestMatcher, List<ConfigAttribute>>();
		LinkedHashMap<Object, List<ConfigAttribute>> data = securedObjectDao.getRolesAndUrl();
		Set<Object> keys = data.keySet();
		for(Object key : keys){
			ret.put((AntPathRequestMatcher)key, data.get(key));
		}
		return ret;
	}

	@Override
	public LinkedHashMap<String, List<ConfigAttribute>> getRolesAndMethod() throws Exception {
		LinkedHashMap<String, List<ConfigAttribute>> ret = new LinkedHashMap<String, List<ConfigAttribute>>();
		LinkedHashMap<Object, List<ConfigAttribute>> data = securedObjectDao.getRolesAndMethod();
		Set<Object> keys = data.keySet();
		for(Object key : keys){
			ret.put((String)key, data.get(key));
		}
		return ret;
	}

	@Override
	public List<ConfigAttribute> getMatchedRequestMapping(String url) throws Exception {
		return securedObjectDao.getRegexMatchedRequestMapping(url);
	}

	@Override
	public String getHierarchicalRoles() throws Exception {
		return securedObjectDao.getHierarchicalRoles();
	}

}
