package com.favccxx.mp.service;

import java.util.List;

import com.favccxx.mp.entity.SmartImage;

public interface ImageService {
	
	/**
	 * 根据图片Id读取图片信息
	 * @param id
	 * @return
	 */
	SmartImage findById(long id);

	/**
	 * 根据产品Id查询缩略图
	 * 
	 * @param productId
	 * @return
	 */
	List<SmartImage> listThumbnailsByProductId(long productId);
	
	/**
	 * 新建或更新图片
	 * @param image
	 */
	void save(SmartImage image);

	/**
	 * 删除图片
	 * @param id
	 */
	void delete(long id);

}
