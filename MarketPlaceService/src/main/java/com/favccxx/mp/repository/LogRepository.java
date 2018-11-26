package com.favccxx.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.SmartLog;

public interface LogRepository extends JpaRepository<SmartLog, Long> {

}
