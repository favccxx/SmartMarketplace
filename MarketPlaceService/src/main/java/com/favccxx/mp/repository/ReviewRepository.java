package com.favccxx.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.SmartReview;

public interface ReviewRepository extends JpaRepository<SmartReview, Long> {

}
