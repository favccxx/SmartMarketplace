package com.favccxx.mp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.mp.entity.SmartMall;
import com.favccxx.mp.repository.MallRepository;
import com.favccxx.mp.service.MallService;

@Service
public class MallServiceImpl implements MallService {

	@Autowired
	MallRepository clubRepository;
	
	@Transactional
	@Override
	public void save(SmartMall club) {
		clubRepository.save(club);
	}

	@Override
	public SmartMall findById(long id) {
		return clubRepository.findById(id).get();
	}

	@Override
	public SmartMall findByMallCode(String mallCode) {
		return clubRepository.findByMallCode(mallCode);
	}

}
