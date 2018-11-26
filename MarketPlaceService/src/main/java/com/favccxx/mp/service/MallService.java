package com.favccxx.mp.service;

import com.favccxx.mp.entity.SmartMall;

public interface MallService {

	/**
	 * 新建或更新商城信息
	 * @param club
	 * @return
	 */
	void save(SmartMall mall);
	
	/**
	 * 根据商城Id查询商城详情
	 * @param id
	 * @return
	 */
	SmartMall findById(long id);
	
	/**
	 * 根据商城代码查询商城详情
	 * @param clubCode
	 * @return
	 */
	SmartMall findByMallCode(String mallCode);
}
