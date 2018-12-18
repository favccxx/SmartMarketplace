package com.favccxx.mp.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.mp.entity.SmartShop;
import com.favccxx.mp.repository.ShopRepository;
import com.favccxx.mp.service.ShopService;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	ShopRepository shopRepository;
	
	@Transactional
	@Override
	public void save(SmartShop shop) {
		shopRepository.save(shop);
	}

	@Override
	public SmartShop findById(long id) {
		return shopRepository.findById(id).get();
	}


	@Override
	public SmartShop findByShopCode(String shopCode) {
		return shopRepository.findByShopCode(shopCode);
	}

	@Override
	public Page<SmartShop> pageQuery(SmartShop shop, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("shopName", GenericPropertyMatchers.startsWith())
				.withIgnorePaths("id");
		
		if(StringUtils.isBlank(shop.getStatus())) {
			matcher.getIgnoredPaths().add("status");
		}else {
			matcher.withMatcher("status", GenericPropertyMatchers.exact());
		}

		Example<SmartShop> example = Example.of(shop, matcher);

		return shopRepository.findAll(example, pageable);
	}

}
