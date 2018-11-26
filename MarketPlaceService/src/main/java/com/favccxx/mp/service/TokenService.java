package com.favccxx.mp.service;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.favccxx.mp.shiro.JWTToken;

public interface TokenService {

	JWTToken saveToken(UsernamePasswordToken token);

	/**
	 * 
	 * @param key
	 * @return
	 */
	UsernamePasswordToken getToken(String key);

	/**
	 * 删除token
	 * @param key
	 */
	boolean deleteToken(String key);
}
