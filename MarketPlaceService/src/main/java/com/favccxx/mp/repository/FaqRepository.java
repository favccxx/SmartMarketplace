package com.favccxx.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.SmartFaq;

public interface FaqRepository extends JpaRepository<SmartFaq, Long> {

}
