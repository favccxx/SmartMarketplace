package com.favccxx.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.mp.constants.CategoryStatus;
import com.favccxx.mp.entity.SmartProductCategory;
import com.favccxx.mp.repository.ProductCategoryRepository;
import com.favccxx.mp.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	
	@Autowired
	ProductCategoryRepository productCategoryRepository;

	@Transactional
	@Override
	public void save(SmartProductCategory productCategory) {
		productCategoryRepository.save(productCategory);
	}

	@Transactional
	@Override
	public void delete(long id) {		
		productCategoryRepository.deleteById(id);
	}

	@Override
	public Page<SmartProductCategory> pageQuery(SmartProductCategory productCategory, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("categoryCode", GenericPropertyMatchers.startsWith())
				.withMatcher("categoryName", GenericPropertyMatchers.startsWith())
				.withMatcher("status", GenericPropertyMatchers.contains())
				.withIgnorePaths("parentId")
				.withIgnorePaths("categoryOrder")
				.withIgnorePaths("id");
		if(productCategory.getStatus()==0) {
			matcher.getIgnoredPaths().add("status");
		}else {
			matcher.withMatcher("status", GenericPropertyMatchers.contains());
		}

		Example<SmartProductCategory> example = Example.of(productCategory, matcher);

		return productCategoryRepository.findAll(example, pageable);
	}

	@Override
	public SmartProductCategory findById(long id) {
		return productCategoryRepository.findById(id).get();
	}

	@Override
	public List<SmartProductCategory> listNormal() {		
		return productCategoryRepository.findByStatus(CategoryStatus.ENABLE.value());
	}

}
