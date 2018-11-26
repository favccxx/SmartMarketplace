package com.favccxx.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.SmartOrderPay;

public interface OrderPayRepository extends JpaRepository<SmartOrderPay, Long> {

}
