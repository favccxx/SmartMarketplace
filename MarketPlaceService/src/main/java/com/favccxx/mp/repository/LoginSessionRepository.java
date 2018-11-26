package com.favccxx.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.LoginSession;

public interface LoginSessionRepository extends JpaRepository<LoginSession, Long> {

	/**
	 * 根据用户名查询登录用户信息
	 * @param token
	 * @return
	 */
	LoginSession findByUsername(String username);
	
	/**
	 * 根据登录令牌查询登录用户信息
	 * @param token
	 * @return
	 */
	LoginSession findByToken(String token);
}
