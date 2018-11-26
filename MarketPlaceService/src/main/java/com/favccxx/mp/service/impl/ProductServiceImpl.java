package com.favccxx.mp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.mp.constants.ProductStatus;
import com.favccxx.mp.entity.SmartProduct;
import com.favccxx.mp.repository.ProductRepository;
import com.favccxx.mp.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Override
	public SmartProduct findById(long id) {
		return productRepository.findById(id).get();
	}

	@Transactional
	@Override
	public void save(SmartProduct product) {
		productRepository.save(product);
	}

	@Transactional
	@Override
	public void delete(long id) {
		SmartProduct product = findById(id);
		product.setStatus(Integer.valueOf(ProductStatus.DELETED.toString()));
		save(product);
	}

	@Override
	public Page<SmartProduct> pageQuery(SmartProduct product, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("productNo", GenericPropertyMatchers.startsWith())
				.withMatcher("productName", GenericPropertyMatchers.startsWith())
				.withMatcher("status", GenericPropertyMatchers.exact())
				.withIgnorePaths("id")
				.withIgnorePaths("workTime")
				.withIgnorePaths("isPackage")
				.withIgnorePaths("originalPrice");
		if(product.getCategoryId()==0) {
			matcher.getIgnoredPaths().add("categoryId");
		}else {
			matcher.withMatcher("categoryId", GenericPropertyMatchers.exact());
		}
		
		if(product.getClubId()==0) {
			matcher.getIgnoredPaths().add("clubId");
		}else {
			matcher.withMatcher("clubId", GenericPropertyMatchers.exact());
		}
		
		if(product.getStar() == 0) {
			matcher.getIgnoredPaths().add("star");
		}else {
			matcher.withMatcher("star", GenericPropertyMatchers.exact());
		}

		Example<SmartProduct> example = Example.of(product, matcher);

		return productRepository.findAll(example, pageable);
	}

	@Override
	public SmartProduct findByProductNo(String productNo) {
		return productRepository.findByProductNo(productNo);
	}

	@Override
	public Page<SmartProduct> pageQuery(String productName, long categoryId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
