package com.favccxx.mp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.favccxx.mp.entity.SmartRole;

public interface RoleRepository extends JpaRepository<SmartRole, Long> {

	
	
	@Query(value = "select distinct r from SmartRole r left join SmartUserRole ur on ur.roleId=r.id and ur.userId=?1")
	List<SmartRole> findByUserId(long userId);
	
}
