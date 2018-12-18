package com.favccxx.mp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.mp.entity.SmartFaq;

public interface FaqService {

	/**
	 * 根据Id查询问答信息
	 * @param id
	 * @return
	 */
	SmartFaq findById(long id);
	
	
	
	/**
	 * 新建或更新问答信息
	 * @param faq
	 */
	void save(SmartFaq faq);
	
	/**
	 * 根据主键删除问答信息
	 * @param id
	 */
	void delete(long id);
	
	/**
	 * 分页查询问答信息
	 * @param faq
	 * @param pageable
	 * @return
	 */
	Page<SmartFaq> pageQuery(SmartFaq faq, Pageable pageable);
}
