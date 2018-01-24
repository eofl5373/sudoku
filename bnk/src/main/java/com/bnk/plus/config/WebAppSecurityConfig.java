package com.bnk.plus.config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.bnk.plus.config.security.ReloadableFilterInvocationSecurityMetadataSource;
import com.bnk.plus.config.security.SecuredObjectService;
import com.bnk.plus.config.security.UrlResourcesMapFactoryBean;
import com.bnk.plus.config.security.handler.AppAccessDeniedHandler;
import com.bnk.plus.config.security.session.SpringSecuritySessionRegistryImpl;

//@EnableWebSecurity
//@Configuration
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {/*
	
	@Autowired private DataSource masterDataSource;
    
    @Autowired SecuredObjectService securedObjectService;
    
	private static Environment env;
	@SuppressWarnings("static-access")
	@Resource
    public void setEnvironment(Environment env) {
    	this.env = env;
    }
	
 #########################################################################################################
 * #####################					  Configure Setting						   #####################
 * ######################################################################################################### 
 
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Type 1 - 일반 ID/PW (=하드코딩)
//		auth
//			.inMemoryAuthentication()
//			.withUser("user").password("123").roles("USER").and()
//			.withUser("admin").password("1234").roles("ADMIN", "USER");

        // Type 2 - 암호화 ID/PW (=하드코딩)
//		auth
//			.inMemoryAuthentication()
//			.passwordEncoder(new ShaPasswordEncoder(256))
//			.withUser("user").password("d033e22ae348aeb5660fc2140aec35850c4da997").roles("USER").and()
//			.withUser("admin").password("12dea96fec20593566ab75692c9949596833adc9").roles("ADMIN", "USER");

        // Type 3 - 암호화 ID/PW (=기본 DB)
    	auth
        	.jdbcAuthentication()
        		.rolePrefix(AppConstBean.SECURITY_ROLE_PREFIX)
                .passwordEncoder(passwordEncoder())
                .dataSource(masterDataSource)
                .usersByUsernameQuery(AppConstBean.SECURITY_USERS_BY_USERNAME_QUERY)
                .authoritiesByUsernameQuery(AppConstBean.SECURITY_AUTHORITIES_BY_USERNAME_QUERY);
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
    	
    	final FilterSecurityInterceptor fsi = filterSecurityInterceptor();
    	web.postBuildAction(new Runnable() {
			@Override
			public void run() {
				web.securityInterceptor(fsi);
			}
		});
    	
    	web.ignoring()
           .antMatchers(AppConstBean.SECURITY_IGNORING_URL_PATTERN);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	String application = env.getProperty("application.state");
    	System.out.println("application ============== " + application);
    	http
	    	.sessionManagement()
			.maximumSessions(AppConstBean.SECURITY_MAXIMUM_SESSIONS)	// 중복 가능한 로그인 세션 수
			.expiredUrl("/session/loginExpired")
//			.maxSessionsPreventsLogin(true)								// 이미 세션이 있을 경우 다른 로그인을 막는다.
			.sessionRegistry(sessionRegistry())							// 세션을 목록에 담아둠
//			.and()
//			.invalidSessionUrl("/session/login?c=to");		// 세션 아웃 됐을 경우 URL Redirect - logout의 경우에도 이 URL로 이동됨
			;
    	
        *//**
         * permitAll : 모든 접근자를 항상 승인
         * denyAll : 모든 사용자의 접근을 거부
         * anonymous : 사용자가 익명 사용자인지 확인
         * authenticated : 인증된 사용자인지 확인
         * rememberMe : 사용자가 remember me를 사용해 인증했는지 확인
         * fullyAuthenticated : 사용자가 모든 크리덴셜을 갖춘 상태에서 인증했는지 확인
         *//*
    	
    	if("PC".equals(application)){
    		http
    		.addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class)
    		
    		// loginProcessingUrl을 404 혹은 not support 'POST' method 에러 발생으로 csrf 사용 안함
    		.csrf().disable()
    		
    		.exceptionHandling()
//                	.accessDeniedPage(AppConstBean.SECURITY_ACCESS_DENIED_PAGE)
    		.accessDeniedHandler(new AppAccessDeniedHandler()) // TODO: DetectAdminUserInterceptor에서 우선 걸러져 효력이 없음...
    		.and()
    		
    		.formLogin()
    		.loginPage(AppConstBean.SECURITY_LOGIN_PAGE)
    		.usernameParameter(AppConstBean.SECURITY_USERNAME_PARAMETER)
    		.passwordParameter(AppConstBean.SECURITY_PASSWORD_PARAMETER)
    		.defaultSuccessUrl(AppConstBean.SECURITY_DEFAULT_SUCCESS_URL)
    		.loginProcessingUrl(AppConstBean.SECURITY_LOGIN_PROCESSING_URL)
    		.failureUrl(AppConstBean.SECURITY_FAILURE_URL)
    		//.successHandler(successHandler())
    		.permitAll()
    		.and()
    		.logout()
    		.logoutUrl("/product/co/logout-proc")
    		.logoutSuccessUrl("/product/index")
    		.deleteCookies("JSESSIONID")
    		.invalidateHttpSession(true) // is Default True
    		.and()
    		.authorizeRequests()
    		.antMatchers("/css/**").permitAll()
//	                .antMatchers("/group", "/group/**").hasRole("ADMIN")        // group, group/** 패턴 URL에 ROLE_ADMIN만 접근 가능
    		.anyRequest().authenticated()								// 다른 모든 URL에 대해 권한 필수
    		.and()
    		// DB - Resource 관리로 변경시 아래의 접근 권한 변경 필요
    		;
    	}else{
    		http
    		.addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class)
    		
    		// loginProcessingUrl을 404 혹은 not support 'POST' method 에러 발생으로 csrf 사용 안함
    		.csrf().disable()
    		
    		.exceptionHandling()
//                	.accessDeniedPage(AppConstBean.SECURITY_ACCESS_DENIED_PAGE)
    		.accessDeniedHandler(new AppAccessDeniedHandler()) // TODO: DetectAdminUserInterceptor에서 우선 걸러져 효력이 없음...
    		.and()
    		
    		.formLogin()
    		.loginPage(AppConstBean.SECURITY_LOGIN_PAGE)
    		.usernameParameter(AppConstBean.SECURITY_USERNAME_PARAMETER)
    		.passwordParameter(AppConstBean.SECURITY_PASSWORD_PARAMETER)
    		.defaultSuccessUrl(AppConstBean.SECURITY_DEFAULT_SUCCESS_URL)
    		.loginProcessingUrl(AppConstBean.SECURITY_LOGIN_PROCESSING_URL)
    		.failureUrl(AppConstBean.SECURITY_FAILURE_URL)
    		//.successHandler(successHandler())
    		.permitAll()
    		.and()
    		.logout()
    		.logoutUrl(AppConstBean.SECURITY_LOGOUT_URL)
    		.logoutSuccessUrl(AppConstBean.SECURITY_LOGOUT_URL)
    		.deleteCookies("JSESSIONID")
    		.invalidateHttpSession(true) // is Default True
    		.and()
    		.authorizeRequests()
    		.antMatchers("/css/**").permitAll()
//	                .antMatchers("/group", "/group/**").hasRole("ADMIN")        // group, group/** 패턴 URL에 ROLE_ADMIN만 접근 가능
    		.anyRequest().authenticated()								// 다른 모든 URL에 대해 권한 필수
    		.and()
    		// DB - Resource 관리로 변경시 아래의 접근 권한 변경 필요
    		;
    	}
				
    }

 #########################################################################################################
 
    

 #########################################################################################################
 * #####################					  Custom Handler						   #####################
 * ######################################################################################################### 
 
    
	public CustomAuthenticationSuccessHandler successHandler() {
//        CustomAuthenticationSuccessHandler successHandler = new CustomAuthenticationSuccessHandler(userService);
//        return successHandler;
        return null;
    }
	
	*//**
	 * 로그인 성공 시 DB의 추가 필요 데이터를 가져와 SecurityUserDetail에 설정해야 할 때 onAuthenticationSuccess() 메소드를 추가 작성하여 사용
	 *//*
    private static class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        
    	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    	
    	T2UserService userService = null;
    	
    	//public CustomAuthenticationSuccessHandler(){}
    	
    	public CustomAuthenticationSuccessHandler(T2UserService userService) {
    		this.userService = userService;
		}
    	
    	@Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

    		// Security session에 추가 정보(Cusom)를 저장한다(Map형태)
    		SecurityContext sec = SecurityContextHolder.getContext();
    		AbstractAuthenticationToken auth = (AbstractAuthenticationToken)sec.getAuthentication();
          
          
    		HashMap<String, Object> info = new HashMap<String, Object>();
    		
    		 * AdviceController.java 에서
    		 * @ModelAttribute("sessUserInfo")로 값을 넣어준다.
    		 * JSP에서 ${sessUserInfo}로 가져올 수 있다.
    		 
//	          getUser.setLoginTime(new Date());
//    		info.put("sessUserInfo", getUser);
    		auth.setDetails(info);
          
    		redirectStrategy.sendRedirect(request, response, determineTargetUrl(authentication, request));
    		
        }
    	
    	*//**
    	 * @title 로그인 성공 후 이동할 URL을 확인하여 리턴
    	 * @description
    	 * 상황을 판단하여 이동되어야하는 URL이 변경이 필요할 경우 수정하여 사용
    	 * 만약 이 SuccessHandler를 사용하고 있지 않을 경우 SessionController.loginSucessProc()를 변경하길 바람
    	 *//*
    	protected String determineTargetUrl(Authentication authentication, HttpServletRequest request) {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		@SuppressWarnings("unchecked")
			HashMap<String, Object> info = (HashMap<String, Object>)auth.getDetails();
    		request.getRequestURL();
    		return "";
    	}
    	
    	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
            this.redirectStrategy = redirectStrategy;
        }
     
        protected RedirectStrategy getRedirectStrategy() {
            return redirectStrategy;
        }
    }
    
 #########################################################################################################
 
    
    
 #########################################################################################################
 * #####################					  URL Filter Interceptor				   #####################
 * ######################################################################################################### 
 
    
    @Bean
	public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception{
		FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
		
		filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean());
		filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
		filterSecurityInterceptor.setSecurityMetadataSource(reloadableFilterInvocationSecurityMetadataSource());
		return filterSecurityInterceptor;
	}
    @Bean
	public AccessDecisionManager accessDecisionManager() throws Exception {
		@SuppressWarnings("rawtypes")
		List<AccessDecisionVoter> decisionVoters = new ArrayList<AccessDecisionVoter>();
		
		 권한의 상속 관계를 입력한다. 
		String hierarchicalRoles = securedObjectService.getHierarchicalRoles();
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy(hierarchicalRoles);
		
		*//**
		 * Voter 의 종류
		 * 			RoleVoter : 리소스에 설정된 Role과 부합하는 접근 권한이 사용자에게 있는지 확인 (role prefix : ROLE_ | 각 롤은 콤마로 구분)
		 * 			AuthenticatedVoter : 와일드 카드와 같은 특수 설정을 지원
		 * 			RoleHierarchyVoter : 상속 관계의 권한 설정 가능
		 * 
		 * Voter 의 접근 권한 관련 값
		 * 			ACCESS_GRANTED : Voter 가 리소스에 대한 접근 권한을 허가하도록 권장
		 * 			ACCESS_DENIED : Voter 가 리소스에 대한 접근 권한을 거부
		 * 			ACCESS_ABSTAIN :  Voter 는 리소스에 대한 접근권한 결정을 보류
		 * 							- Voter 가 접근권한 판단에 필요한 결정적인 정보를 가지고 있지 않은 경우
		 * 							- Voter 가 해당 타입의 요청에 대해 결정을 내릴 수 없는 경우
		 *//*
		RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchyImpl);
		roleHierarchyVoter.setRolePrefix(AppConstBean.SECURITY_ROLE_PREFIX);
		decisionVoters.add(roleHierarchyVoter);
		
		*//**
		 * AffirmativeBased : 접근을 승인하는 voter 가 하나라도 존재하면 이전의 접근 거부사실과 관계없이 바로 접근이 승인
		 * ConsensusBased : 다득표 여부가 AccessDecisionManager 의 접근 승인여부를 결정. 동률 또는 무효표에 처리에 대해서는 설정 가능
		 * UnanimouseBased : 모든 voter 가 접근을 승인해야 최종적인 접근이 승인
		 *//*
		AffirmativeBased affirmativeBased = new AffirmativeBased(decisionVoters);
		affirmativeBased.setAllowIfAllAbstainDecisions(false);
		
		return affirmativeBased;
    }
	@Bean
	public ReloadableFilterInvocationSecurityMetadataSource reloadableFilterInvocationSecurityMetadataSource() throws Exception{
		return new ReloadableFilterInvocationSecurityMetadataSource(requestMap().getObject());
	}
	@Bean
	public UrlResourcesMapFactoryBean requestMap() throws Exception{
		UrlResourcesMapFactoryBean urlResourcesMapFactoryBean = new UrlResourcesMapFactoryBean();
		urlResourcesMapFactoryBean.init(securedObjectService);
		return urlResourcesMapFactoryBean;
	}
	
 #########################################################################################################
 
	
	@Bean
    public StandardPasswordEncoder passwordEncoder(){
    	return new StandardPasswordEncoder(AppConstBean.StandardPasswordEncoderSalt);
    }
	
	public static void setDetails(HashMap<String, Object> detail){
		SecurityContext sec = SecurityContextHolder.getContext();
        AbstractAuthenticationToken auth = (AbstractAuthenticationToken)sec.getAuthentication();
        HashMap<String, Object> info = (HashMap<String, Object>)auth.getDetails();
        for(String key : detail.keySet()){
        	info.put(key, detail.get(key));
        }
        auth.setDetails(info);
        sec.setAuthentication(auth);
        SecurityContextHolder.setContext(sec);
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SpringSecuritySessionRegistryImpl();
	}
	
	@Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
            return new HttpSessionEventPublisher();
    }
	
*/}