package com.favccxx.mp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.mp.entity.SmartUser;

/**
 * 用户管理接口
 * @author favccxx
 *
 */
public interface UserService {

	/**
	 * 根据Id查询用户信息
	 * @param id
	 * @return
	 */
	SmartUser findById(long id);
	
	/**
	 * 根据用户名查询用户信息
	 * @param userName
	 * @return
	 */
	SmartUser findByUserName(String userName);
	
	/**
	 * 新建或更新用户信息
	 * @param vip
	 */
	void save(SmartUser user);
	
	/**
	 * 根据主键删除用户信息
	 * @param id
	 */
	void delete(long id);
	
	/**
	 * 分页查询用户信息
	 * @param vip
	 * @param pageable
	 * @return
	 */
	Page<SmartUser> pageQuery(SmartUser user, Pageable pageable);
	
	/**
	 * 根据用户Id查询角色代码列表
	 * @param userId
	 * @return
	 */
	List<String> findRoleCodesByUserId(long userId);
}
