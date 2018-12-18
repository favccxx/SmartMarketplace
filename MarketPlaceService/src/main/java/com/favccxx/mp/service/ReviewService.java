package com.favccxx.mp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.mp.entity.SmartReview;

public interface ReviewService {

	/**
	 * 根据Id查询评论信息
	 * @param id
	 * @return
	 */
	SmartReview findById(long id);
	
	
	
	/**
	 * 新建或更新评论信息
	 * @param product
	 */
	void save(SmartReview review);
	
	/**
	 * 根据主键删除评论信息
	 * @param id
	 */
	void delete(long id);
	
	/**
	 * 分页查询评论信息
	 * @param product
	 * @param pageable
	 * @return
	 */
	Page<SmartReview> pageQuery(SmartReview review, Pageable pageable);
	
	
	
}
