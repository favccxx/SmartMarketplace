package com.favccxx.mp.service;

import com.favccxx.mp.entity.LoginSession;

public interface SessionUserService {
	
	/**
	 * 根据用户名查询或创建Session
	 * @param userName
	 * @return
	 */
	LoginSession findOrCreateByUserName(String userName);
	
	
	/**
	 * 根据登录令牌查询登录用户信息
	 * @param token
	 * @return
	 */
	LoginSession findByToken(String token);

}
