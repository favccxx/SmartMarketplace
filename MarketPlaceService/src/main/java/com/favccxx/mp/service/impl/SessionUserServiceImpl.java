package com.favccxx.mp.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.mp.entity.LoginSession;
import com.favccxx.mp.entity.SmartUser;
import com.favccxx.mp.repository.LoginSessionRepository;
import com.favccxx.mp.repository.UserRepository;
import com.favccxx.mp.service.SessionUserService;
import com.favccxx.mp.utils.JWTUtil;

@Service
public class SessionUserServiceImpl implements SessionUserService {
	
	@Autowired
	LoginSessionRepository loginSessionRepository;
	@Autowired
	UserRepository userRepository;

	@Transactional
	@Override
	public LoginSession findOrCreateByUserName(String username) {
		LoginSession userSession = loginSessionRepository.findByUsername(username);
		//新建Token
		if(userSession == null) {
			SmartUser user = userRepository.findByUsername(username);
			if(user == null) {
				return null;
			}

			userSession = new LoginSession();
			userSession.setUsername(user.getUsername());
			try {
				String token = JWTUtil.sign(username, "xxx");
				userSession.setToken(token);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			userSession.setSession("adminSession");
			userSession.setRoles("admin,editor");			
			userSession.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");			
			userSession.setIntroduction("我的超级管理员");
			userSession.setCreateDate(new Date());
			userSession.setStatus(1);
			loginSessionRepository.save(userSession);
			
			return userSession;
			
		}else {
			//更新Token的过期时间
			String token = userSession.getToken();
			//
			boolean flag = JWTUtil.verify(token, username, "xxx");
			if(!flag) {
				String newToken;
				try {
					newToken = JWTUtil.sign(username, "xxx");
					userSession.setToken(newToken);
					loginSessionRepository.save(userSession);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
		
		return userSession;
	}

	@Override
	public LoginSession findByToken(String token) {
		return loginSessionRepository.findByToken(token);
	}

}
