package com.favccxx.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.mp.constants.SysConstants;
import com.favccxx.mp.entity.SmartImage;
import com.favccxx.mp.repository.ImageRepository;
import com.favccxx.mp.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	ImageRepository imageRepository;

	@Override
	public List<SmartImage> listThumbnailsByProductId(long productId) {
		return imageRepository.findByProductIdAndType(productId, SysConstants.IMAGE_SLIDER);
	}

	@Transactional
	@Override
	public void save(SmartImage image) {
		imageRepository.save(image);
	}

	@Transactional
	@Override
	public void delete(long id) {
		imageRepository.deleteById(id);	
	}

	@Override
	public SmartImage findById(long id) {
		return imageRepository.findById(id).get();
	}

}
