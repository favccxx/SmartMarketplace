package com.favccxx.mp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.SmartProductCategory;

public interface ProductCategoryRepository extends JpaRepository<SmartProductCategory, Long> {
	
	/**
	 * 根据类别状态查询产品类别信息列表
	 * @param status 类别状态
	 * @return
	 */
	List<SmartProductCategory> findByStatus(int status);

}
