package com.favccxx.mp.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.favccxx.mp.constants.SysConstants;
import com.favccxx.mp.utils.JWTUtil;

public class JWTFilter extends BasicHttpAuthenticationFilter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 判断用户是否进行登录
	 */
	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		logger.info("----------------JWTFilter.isLoginAttempt---------------------");
		HttpServletRequest req = (HttpServletRequest) request;
		String authorization = req.getHeader(SysConstants.HEADER_TOKEN_KEY);
		logger.info("----------------authorization---------------------" + authorization);
		return authorization != null;
	}

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		logger.info("----------------executeLogin---------------------");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String authorization = httpServletRequest.getHeader(SysConstants.HEADER_TOKEN_KEY);
//
//		String username = JwtUtil.getUsername(authorization);
//		
		JWTToken token = new JWTToken(authorization);
//		// 提交给realm进行登入，如果错误他会抛出异常并被捕获
		getSubject(request, response).login(token);
		// 如果没有抛出异常则代表登入成功，返回true
		return true;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		logger.info("----------------isAccessAllowed---------------------" + isLoginAttempt(request, response));
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String url = httpServletRequest.getRequestURI();
		logger.info("--------------------isAccessAllowed-URL--------------" + url);
		if (url.contains("login")) {
			return true;
		}
		
		try {
			executeLogin(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Subject subject = getSubject(request, response);
//		if ((null != subject && !subject.isAuthenticated())) {
//			String authorization = httpServletRequest.getHeader(SysConstants.HEADER_TOKEN_KEY);
//			AuthenticationToken token = new JWTToken(authorization);			
//				subject.login(token);
//		}


		return true;
	}

	/**
	 * 提供跨域支持
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		logger.info("--------------------preHandle---------------");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				httpServletRequest.getHeader("Access-Control-Request-Headers"));
		// 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
	}

//	@Override
//	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
//		 if (isLoginRequest(request, response)) {
//			 HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//			 String authorization = httpServletRequest.getHeader("token");
//
//			 if(StringUtils.isNoneBlank(authorization)) {
//				 String username = JwtUtil.getUsername(authorization);
//				 
//			 }
//			 
//	            if (json != null && !json.isEmpty()) {
//
//	                try (JsonReader jr = Json.createReader(new StringReader(json))) {
//	                    JsonObject object = jr.readObject();
//	                    String username = object.getString(USER_ID);
//	                    String password = object.getString(PASSWORD);
//	                    return new UsernamePasswordToken(username, password);
//	                }
//
//	            }
//	        }
//
//	        if (isLoggedAttempt(request, response)) {
//	            String jwtToken = getAuthzHeader(request);
//	            if (jwtToken != null) {
//	                return createToken(jwtToken);
//	            }
//	        }
//
//	        return new UsernamePasswordToken();
//	}

}
