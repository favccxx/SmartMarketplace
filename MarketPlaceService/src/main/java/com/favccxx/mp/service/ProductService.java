package com.favccxx.mp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.mp.entity.SmartProduct;

public interface ProductService {

	/**
	 * 根据Id查询产品信息
	 * @param id
	 * @return
	 */
	SmartProduct findById(long id);
	
	/**
	 * 根据产品编码查询产品详情
	 * @param productNo
	 * @return
	 */
	SmartProduct findByProductNo(String productNo);
	
	/**
	 * 新建或更新产品信息
	 * @param product
	 */
	void save(SmartProduct product);
	
	/**
	 * 根据主键删除产品信息
	 * @param id
	 */
	void delete(long id);
	
	/**
	 * 分页查询产品信息
	 * @param product
	 * @param pageable
	 * @return
	 */
	Page<SmartProduct> pageQuery(SmartProduct product, Pageable pageable);
	
	
	/**
	 * 根据产品类别和名称查询产品列表
	 * @param productName
	 * @param categoryId
	 * @param pageable
	 * @return
	 */
	Page<SmartProduct> pageQuery(String productName, long categoryId, Pageable pageable);
	
}
