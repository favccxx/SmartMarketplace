package com.favccxx.mp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.mp.entity.SmartRoom;

public interface RoomService {

	/**
	 * 根据Id查询房间信息
	 * @param id
	 * @return
	 */
	SmartRoom findById(long id);
	
	/**
	 * 新建或更新会员信息
	 * @param room
	 */
	void save(SmartRoom room);
	
	/**
	 * 根据主键删除会员信息
	 * @param id
	 */
	void delete(long id);
	
	/**
	 * 分页查询会员信息
	 * @param room
	 * @param pageable
	 * @return
	 */
	Page<SmartRoom> pageQuery(SmartRoom room, Pageable pageable);
	
}
