package com.favccxx.mp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.SmartVip;

public interface VipRepository extends JpaRepository<SmartVip, Long> {
	
	
	
	
//	Page<SmartVip> pageQuery(Specification<SmartVip> spec, Pageable pageable);

}
