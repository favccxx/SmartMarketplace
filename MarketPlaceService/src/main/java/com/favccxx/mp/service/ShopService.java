package com.favccxx.mp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.mp.entity.SmartShop;

public interface ShopService {

	/**
	 * 新建或更新商城信息
	 * @param shop
	 * @return
	 */
	void save(SmartShop shop);
	
	/**
	 * 根据商城Id查询商城详情
	 * @param id
	 * @return
	 */
	SmartShop findById(long id);
	
	/**
	 * 根据商城代码查询商城详情
	 * @param shopCode
	 * @return
	 */
	SmartShop findByShopCode(String shopCode);
	
	/**
	 * 分页查询店铺信息
	 * @param shop
	 * @param pageable
	 * @return
	 */
	Page<SmartShop> pageQuery(SmartShop shop, Pageable pageable);
}
