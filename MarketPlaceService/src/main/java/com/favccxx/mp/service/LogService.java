package com.favccxx.mp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.mp.entity.SmartLog;

public interface LogService {

	/**
	 * 分页查询审计日志信息
	 * @param log
	 * @param pageable
	 * @return
	 */
	Page<SmartLog> pageQuery(SmartLog log, Pageable pageable);
	
}
