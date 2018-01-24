package com.bnk.plus.config.security;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Repository;

@Repository(value="securedObjectDao")
public class SecuredObjectDao {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// URL 형식 자원 조회 쿼리
	public static final String DEF_ROLES_AND_URL_QUERY = 
			"SELECT a.resource_pattern AS url, b.authority AS authority "
			+ "FROM "
				+ "T2_SECURED_RESOURCES a, T2_SECURED_RESOURCES_ROLE b "
			+ "WHERE "
				+ "a.resource_id = b.resource_id "
				+ "AND a.resource_type = 'URL' "
			+ "ORDER BY a.sort_order";
	// Method 형식 자원 조회 쿼리
	public static final String DEF_ROLES_AND_METHOD_QUERY=
			"SELECT a.resource_pattern AS method, b.authority AS authority "
			+ "FROM "
				+ "T2_SECURED_RESOURCES a, T2_SECURED_RESOURCES_ROLE b "
			+ "WHERE "
				+ "a.resource_id = b.resource_id "
				+ "AND a.resource_type = 'METHOD' "
			+ "ORDER BY a.sort_order";
	// 매 Request Role 맵핑 정보를 얻기 위한 Default 쿼리
	// TODO 미개발 2015/04/06
	public static final String DEF_REGEX_MATCHED_REQUEST_MAPPING_QUERY_ORACLE10G="SELECT * FROM T2_USERS";
	public static final String DEF_REGEX_MATCHED_REQUEST_MAPPING_QUERY_MYSQL="SELECT * FROM T2_USERS";
	// Role의 계층 관계를 조회하기 위한 Default 쿼리
	public static final String DEF_HIERARCHICAL_ROLES_QUERY = 
			"SELECT a.child_role child, a.parent_role parent "
			+ "FROM "
			+ "T2_ROLES_HIERARCHY a LEFT JOIN T2_ROLES_HIERARCHY b ON (a.child_role = b.parent_role)";
	
	private String sqlRolesAndUrl;
	private String sqlRolesAndMethod;
	private String sqlRegexMatchedRequestMapping;
	private String sqlHierarchicalRoles;
	
	/* 생성자 */
	public SecuredObjectDao (){
		this.sqlRolesAndUrl = DEF_ROLES_AND_URL_QUERY;
		this.sqlRolesAndMethod = DEF_ROLES_AND_METHOD_QUERY;
		this.sqlRegexMatchedRequestMapping = DEF_REGEX_MATCHED_REQUEST_MAPPING_QUERY_MYSQL;
		this.sqlHierarchicalRoles = DEF_HIERARCHICAL_ROLES_QUERY;
	}
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public String getSqlRolesAndUrl(){
		return sqlRolesAndUrl;
	}
	
	public void setSqlRolesAndUrl(String sqlRolesAndUrl){
		this.sqlRolesAndUrl = sqlRolesAndUrl;
	}
	public String getSqlRolesAndMethod(){
		return sqlRolesAndMethod;
	}
	public void setSqlRolesAndMethod(String sqlRolesAndMethod){
		this.sqlRolesAndMethod = sqlRolesAndMethod;
	}
	public String getSqlRegexMatchedRequestMapping(){
		return sqlRegexMatchedRequestMapping;
	}
	public void setSqlRegexMathcedRequestMapping(String sqlRegecMatchedRequestMapping){
		this.sqlRegexMatchedRequestMapping = sqlRegecMatchedRequestMapping;
	}
	public String getSqlHierarchicalRoles(){
		return sqlHierarchicalRoles;
	}
	public void setSqlHierarchicalRoles(String sqlHierarchicalRoles){
		this.sqlHierarchicalRoles = sqlHierarchicalRoles;
	}
	
	public LinkedHashMap<Object, List<ConfigAttribute>> getRolesAndResources(String resourceType) throws Exception{
		LinkedHashMap<Object, List<ConfigAttribute>> resourcesMap = new LinkedHashMap<Object, List<ConfigAttribute>>();
		
		String sqlRolesAndResources;
		boolean isResourcesUrl = true;
		if("method".equals(resourceType)){
			sqlRolesAndResources = getSqlRolesAndMethod();
			isResourcesUrl = false;
		}else{
			sqlRolesAndResources = getSqlRolesAndUrl();
		}
		
		List<Map<String, Object>> resultList = this.namedParameterJdbcTemplate.queryForList(sqlRolesAndResources, new HashMap<String, String>());
		Iterator<Map<String, Object>> itr = resultList.iterator();
		Map<String, Object> tempMap;
		String preResource = null;
		String presentResourceStr;
		Object presentResource;
		while (itr.hasNext()) {
			tempMap = itr.next();

			presentResourceStr = (String) tempMap.get(resourceType);
			// url 인 경우 RequestKey 형식의 key를 Map에 담아야 함
			presentResource = isResourcesUrl ? new AntPathRequestMatcher(presentResourceStr) : presentResourceStr;
			List<ConfigAttribute> configList = new LinkedList<ConfigAttribute>();

			// 이미 requestMap 에 해당 Resource 에 대한 Role 이 하나 이상 맵핑되어 있었던 경우,
			// sort_order 는 resource(Resource) 에 대해 매겨지므로 같은 Resource 에 대한 Role 맵핑은 연속으로 조회됨.
			// 해당 맵핑 Role List (SecurityConfig) 의 데이터를 재활용하여 새롭게 데이터 구축
			if (preResource != null && presentResourceStr.equals(preResource)) {
				List<ConfigAttribute> preAuthList = resourcesMap.get(presentResource);
				Iterator<ConfigAttribute> preAuthItr = preAuthList.iterator();
				while (preAuthItr.hasNext()) {
					SecurityConfig tempConfig = (SecurityConfig) preAuthItr.next();
					configList.add(tempConfig);
				}
			}

			/*
			 * Table SECURED_RESOURCES_ROLE.authority 에 N개의 권한 맵핑시 콤바 구분으로 다중 권한 부여 가능 
			 * (* 권한의 상속관계를 필히 확인하여 사용할 것)
			 */
			String authority = (String) tempMap.get("authority");
			String authorityArray[] = authority.split(",");
			for(String authorityVal : authorityArray){
				configList.add(new SecurityConfig(authorityVal.trim()));
			}
			
			// 만약 동일한 Resource 에 대해 한개 이상의 Role 맵핑 추가인 경우
			// 이전 resourceKey 에 현재 새로 계산된 Role 맵핑 리스트로 덮어쓰게 됨.
			resourcesMap.put(presentResource, configList);

			// 이전 resource 비교위해 저장
			preResource = presentResourceStr;
		}
		return resourcesMap;
	}
	
	public LinkedHashMap<Object, List<ConfigAttribute>> getRolesAndUrl() throws Exception {
		return getRolesAndResources("url");
	}

	public LinkedHashMap<Object, List<ConfigAttribute>> getRolesAndMethod() throws Exception {
		return getRolesAndResources("method");
	}

	public LinkedHashMap<Object, List<ConfigAttribute>> getRolesAndPointcut() throws Exception {
		return getRolesAndResources("pointcut");
	}
	
	public List<ConfigAttribute> getRegexMatchedRequestMapping(String url) throws Exception {

		// best regex matching - best 매칭된 Uri 에 따른 Role List 조회,
		// DB 차원의 정규식 지원이 있는 경우 사용 (ex. hsqldb custom function, Oracle 10g regexp_like 등)
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("url", url);
		List<Map<String, Object>> resultList = this.namedParameterJdbcTemplate.queryForList(getSqlRegexMatchedRequestMapping(), paramMap);

		Iterator<Map<String, Object>> itr = resultList.iterator();
		Map<String, Object> tempMap;
		List<ConfigAttribute> configList = new LinkedList<ConfigAttribute>();
		// 같은 Uri 에 대한 Role 맵핑이므로 무조건 configList 에 add 함
		while (itr.hasNext()) {
			tempMap = itr.next();
			configList.add(new SecurityConfig((String)tempMap.get("authority")));
		}

		if (configList.size() > 0) {
			logger.debug("Request Uri : {}, matched Uri : {}, mapping Roles : {}", url, resultList.get(0).get("uri"), configList);
		}
		return configList;
	}

	public String getHierarchicalRoles() throws Exception {

		List<Map<String, Object>> resultList = this.namedParameterJdbcTemplate.queryForList(getSqlHierarchicalRoles(), new HashMap<String, String>());

		Iterator<Map<String, Object>> itr = resultList.iterator();
		StringBuffer concatedRoles = new StringBuffer();
		Map<String, Object> tempMap;
		while (itr.hasNext()) {
			tempMap = itr.next();
			concatedRoles.append(tempMap.get("child"));
			concatedRoles.append(" > ");
			concatedRoles.append(tempMap.get("parent"));
			concatedRoles.append("\n");
		}
		return concatedRoles.toString();
	}
}