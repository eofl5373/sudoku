package com.bnk.plus.config.security.session;

import org.springframework.security.core.session.SessionRegistryImpl;

/**
 * <pre>
 * SpringSecuritySessionRegistryImpl.java
 * 
 * @Autowired SessionRegistry sessionRegistry; 로 주입받아
 * List<Object> sessionRegistries = sessionRegistry.getAllPrincipals(); 로 생성된 세션들을 확인할 수 있다.
 * </pre>
 *
 * @author ks-choi
 * @date 2016. 4. 17.
 */
public class SpringSecuritySessionRegistryImpl extends SessionRegistryImpl {}