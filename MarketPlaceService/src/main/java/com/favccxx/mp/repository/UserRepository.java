package com.favccxx.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.SmartUser;

public interface UserRepository extends JpaRepository<SmartUser, Long> {

	SmartUser findByUsername(String username);
	
}
