package com.favccxx.mp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.mp.entity.SmartProductCategory;

public interface ProductCategoryService {
	
	/**
	 * 根据商品类别Id查询商品详情
	 * @param id
	 * @return
	 */
	SmartProductCategory findById(long id);

	/**
	 * 新建或更新产品类别
	 * @param productCategory
	 */
	void save(SmartProductCategory productCategory);
	
	/**
	 * 删除产品类别
	 * @param id
	 */
	void delete(long id);
	
	/**
	 * 分页查询产品类别
	 * @param productCategory
	 * @param pageable
	 * @return
	 */
	Page<SmartProductCategory> pageQuery(SmartProductCategory productCategory, Pageable pageable);
	
	/**
	 * 查询所有正常状态的类别信息
	 * @return
	 */
	List<SmartProductCategory> listNormal();
	
	/**
	 * 查询所有的父类别列表
	 * @return
	 */
	List<SmartProductCategory> listParent();
}
