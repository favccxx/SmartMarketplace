package com.favccxx.mp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.mp.entity.SmartRole;
import com.favccxx.mp.entity.SmartUser;
import com.favccxx.mp.repository.RoleRepository;
import com.favccxx.mp.repository.UserRepository;
import com.favccxx.mp.repository.UserRoleRepository;
import com.favccxx.mp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRoleRepository userRoleRepository;

	@Override
	public SmartUser findById(long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public SmartUser findByUserName(String username) {
		return userRepository.findByUsername(username);
	}

	@Transactional
	@Override
	public void save(SmartUser user) {
		userRepository.save(user);
	}

	@Transactional
	@Override
	public void delete(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public Page<SmartUser> pageQuery(SmartUser user, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("userName", GenericPropertyMatchers.contains())
				.withMatcher("nickName", GenericPropertyMatchers.contains())
				.withMatcher("status", GenericPropertyMatchers.contains())
				.withIgnorePaths("id");

		Example<SmartUser> example = Example.of(user, matcher);

		return userRepository.findAll(example, pageable);
	}

	@Override
	public List<String> findRoleCodesByUserId(long userId) {
		List<SmartRole> roleList = roleRepository.findByUserId(userId);
		List<String> list = new ArrayList<String>();
		for(SmartRole role : roleList) {
			list.add(role.getRoleCode());
		}
		return list;
	}

}
