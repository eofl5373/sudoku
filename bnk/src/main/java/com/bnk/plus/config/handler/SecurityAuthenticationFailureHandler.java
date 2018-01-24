package com.bnk.plus.config.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class SecurityAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
//	@Autowired UserMapper userMapper;
//	@Autowired UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

	public static final String LAST_USERNAME_KEY = "LAST_USERNAME";
	public static final String LAST_USER_STATUS_KEY = "LAST_USER_STATUS";
	public static String DEFAULT_TARGET_PARAMETER = "spring-security-redirect-login-failure";
	private String targetUrlParameter = DEFAULT_TARGET_PARAMETER;

	public String getTargetUrlParameter() {
		return targetUrlParameter;
	}

	public void setTargetUrlParameter(String targetUrlParameter) {
		this.targetUrlParameter = targetUrlParameter;
	}
	
	@Override
	public void setDefaultFailureUrl(String defaultFailureUrl) {
		super.setDefaultFailureUrl(defaultFailureUrl);
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

//		String loginUsername = usernamePasswordAuthenticationFilter.getUsernameParameter();
		/*
		String loginUsername = request.getParameter(AppConstBean.SECURITY_USERNAME_PARAMETER);
		String loginUserPwd = request.getParameter(AppConstBean.SECURITY_PASSWORD_PARAMETER);
		loginUserPwd = CryptUtil.encryptSha256(loginUserPwd);
		
		if (StringUtil.isNotEmpty(loginUsername)){
			User user = new User();
			user.setUserId(loginUsername);
			user.setUserPwd(loginUserPwd);
			user = userMapper.getUserDetail(user);
		
			HttpSession session = request.getSession(false);
		    if (session != null || isAllowSessionCreation()) {
		        request.getSession().setAttribute(LAST_USERNAME_KEY, loginUsername);
		        if(user != null){
		        	request.getSession().setAttribute(LAST_USER_STATUS_KEY, user.getUseStatusCd());
		        }
		    }
		}
		*/
		String accept = request.getHeader("accept");

		String error = "true";
		String message = "로그인실패하였습니다.";

		// IE에서는 accept Type에 text/html을 넘겨주지 않아 요청에 따른 처리를 할 수 없어 무조건 response를 redirect 시키도록 함
//		if (StringUtils.indexOf(accept, "html") > -1) {

			String redirectUrl = request.getParameter(this.targetUrlParameter);
			if (redirectUrl != null) {
				super.logger.debug("Found redirect URL: " + redirectUrl);
				getRedirectStrategy().sendRedirect(request, response, redirectUrl);
			} else {
				super.onAuthenticationFailure(request, response, exception);
			}

//		} else if (StringUtils.indexOf(accept, "xml") > -1) {
//			response.setContentType("application/xml");
//			response.setCharacterEncoding("utf-8");
//
//			String data = StringUtils.join(new String[] {
//					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "<response>",
//					"<error>", error, "</error>", "<message>", message,
//					"</message>", "</response>" });
//
//			PrintWriter out = response.getWriter();
//			out.print(data);
//			out.flush();
//			out.close();
//
//		} else if (StringUtils.indexOf(accept, "json") > -1) {
//			response.setContentType("application/json");
//			response.setCharacterEncoding("utf-8");
//
//			String data = StringUtils.join(new String[] {
//					" { \"response\" : {", " \"error\" : ", error, ", ",
//					" \"message\" : \"", message, "\" ", "} } " });
//
//			PrintWriter out = response.getWriter();
//			out.print(data);
//			out.flush();
//			out.close();
//		}
	}
}
