package com.favccxx.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.SmartMall;

public interface MallRepository extends JpaRepository<SmartMall, Long> {

	/**
	 * 根据商城代码查询商城详情
	 * @param mallCode
	 * @return
	 */
	SmartMall findByMallCode(String mallCode);
	
}
