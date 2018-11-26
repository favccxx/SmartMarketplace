package com.favccxx.mp.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.favccxx.mp.entity.SmartUser;
import com.favccxx.mp.service.UserService;
import com.favccxx.mp.utils.JWTUtil;

@Component
public class MyRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserService userService;

	/**
	 * 必须定义token属于JwtToken，否则会报错
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token != null && token instanceof JWTToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = JWTUtil.getUsername(principals.toString());
		logger.info("------------doGetAuthorizationInfo.username--------------" + username);
		SmartUser user = userService.findByUserName(username);

		// 配置角色
		List<String> roles = userService.findRoleCodesByUserId(user.getId());
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addRoles(roles);
		logger.info("------------doGetAuthorizationInfo.roles--------------" + roles.toString());

		//simpleAuthorizationInfo.set
		
		// 配置权限
//        Set<String> permission = ;
//        simpleAuthorizationInfo.addStringPermissions(permission);

		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		String token = (String) authToken.getPrincipal();
//		String password = (String) authToken.getCredentials();

		String username = JWTUtil.getUsername(token);
		logger.info("---------doGetAuthenticationInfo.username----------" + username);

		
		if (username == null) {
			throw new AuthenticationException("token无效");
		}

		SmartUser user = userService.findByUserName(username);
		if (user == null) {
			throw new AuthenticationException("用户不存在!");
		}
 
        if (!JWTUtil.verify(token, username, user.getPassword())) {
            throw new AuthenticationException("用户名或密码错误");
        }

		return new SimpleAuthenticationInfo(token, token, getName());
	}

}
