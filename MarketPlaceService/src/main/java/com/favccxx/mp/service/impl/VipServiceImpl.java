package com.favccxx.mp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.mp.entity.SmartVip;
import com.favccxx.mp.repository.VipRepository;
import com.favccxx.mp.service.VipService;

@Service
public class VipServiceImpl implements VipService {
	
	@Autowired
	VipRepository vipRepository;

	@Override
	public SmartVip findById(long id) {
		return vipRepository.findById(id).get();
	}

	@Transactional
	@Override
	public void save(SmartVip vip) {
		vipRepository.save(vip);
	}

	@Transactional
	@Override
	public void delete(long id) {
		vipRepository.deleteById(id);
	}

	@Override
	public Page<SmartVip> pageQuery(SmartVip vip, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("vipName", GenericPropertyMatchers.contains())
				.withMatcher("vipTel", GenericPropertyMatchers.startsWith())
				.withIgnorePaths("id")
				.withIgnorePaths("vipStar")
				.withIgnorePaths("vipFee");
		

		Example<SmartVip> example = Example.of(vip, matcher);

		return vipRepository.findAll(example, pageable);
	}

}
