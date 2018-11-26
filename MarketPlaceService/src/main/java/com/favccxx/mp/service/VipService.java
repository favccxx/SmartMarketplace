package com.favccxx.mp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.mp.entity.SmartVip;

public interface VipService {
	
	/**
	 * 根据Id查询会员信息
	 * @param id
	 * @return
	 */
	SmartVip findById(long id);
	
	/**
	 * 新建或更新会员信息
	 * @param vip
	 */
	void save(SmartVip vip);
	
	/**
	 * 根据主键删除会员信息
	 * @param id
	 */
	void delete(long id);
	
	/**
	 * 分页查询会员信息
	 * @param vip
	 * @param pageable
	 * @return
	 */
	Page<SmartVip> pageQuery(SmartVip vip, Pageable pageable);

}
