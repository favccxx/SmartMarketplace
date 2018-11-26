package com.favccxx.mp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.SmartUserRole;

public interface UserRoleRepository extends JpaRepository<SmartUserRole, Long> {

	/**
	 * 根据用户Id查询用户角色之间的关联关系	
	 * @param userId
	 * @return
	 */	
	List<SmartUserRole> findByUserId(long userId);
	
}
