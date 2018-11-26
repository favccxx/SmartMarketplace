package com.favccxx.mp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.mp.entity.SmartRole;
import com.favccxx.mp.repository.RoleRepository;
import com.favccxx.mp.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public SmartRole findById(long id) {
		return roleRepository.findById(id).get();
	}

	@Transactional
	@Override
	public void save(SmartRole role) {
		roleRepository.save(role);
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<SmartRole> pageQuery(SmartRole role, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("roleCode", GenericPropertyMatchers.exact())
				.withMatcher("roleName", GenericPropertyMatchers.startsWith())
				.withIgnorePaths("id")
				.withIgnorePaths("status");

		Example<SmartRole> example = Example.of(role, matcher);

		return roleRepository.findAll(example, pageable);
	}

}
