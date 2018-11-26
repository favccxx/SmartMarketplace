package com.favccxx.mp.service.impl;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.favccxx.mp.service.TokenService;
import com.favccxx.mp.shiro.JWTToken;

//@Service
public class TokenServiceImpl implements TokenService {
	
//	@Autowired
//	CacheManager cacheManager;

	@Override
	public JWTToken saveToken(UsernamePasswordToken token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsernamePasswordToken getToken(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteToken(String key) {
		// TODO Auto-generated method stub
		return false;
	}

}
