package com.favccxx.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.SmartShop;

public interface ShopRepository extends JpaRepository<SmartShop, Long> {

	/**
	 * 根据店铺代码查询商城详情
	 * @param mallCode
	 * @return
	 */
	SmartShop findByShopCode(String shopCode);
	
}
