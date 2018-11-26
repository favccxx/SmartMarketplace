package com.favccxx.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.SmartProduct;

public interface ProductRepository extends JpaRepository<SmartProduct, Long> {

	/**
	 * 根据产品编码查询产品详情
	 * @param productNo
	 * @return
	 */
	SmartProduct findByProductNo(String productNo);
	
}
