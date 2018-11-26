package com.favccxx.mp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.mp.entity.SmartRole;

/**
 * 角色管理接口
 * @author favccxx
 *
 */
public interface RoleService {

	/**
	 * 根据Id查询角色信息
	 * @param id
	 * @return
	 */
	SmartRole findById(long id);
	
	
	/**
	 * 新建或更新角色信息
	 * @param role
	 */
	void save(SmartRole role);
	
	/**
	 * 根据主键删除角色
	 * @param id
	 */
	void delete(long id);
	
	/**
	 * 分页查询角色信息
	 * @param role
	 * @param pageable
	 * @return
	 */
	Page<SmartRole> pageQuery(SmartRole role, Pageable pageable);
	
}
