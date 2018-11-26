package com.favccxx.mp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.SmartImage;

public interface ImageRepository  extends JpaRepository<SmartImage, Long> {

	/**
	 * 根据产品Id和图片类型查询图片列表
	 * @param productId
	 * @return
	 */
	List<SmartImage> findByProductIdAndType(long productId, String type);

}
