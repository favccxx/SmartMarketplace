package com.favccxx.mp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.SmartOrderProduct;

/**
 * 订单产品接口
 * @author favccxx
 *
 */
public interface OrderProductRepository extends JpaRepository<SmartOrderProduct, Long> {
	
	/**
	 * 根据订单Id查询订单产品列表
	 * @param orderId
	 * @return
	 */
	List<SmartOrderProduct> findByOrderId(long orderId);

}
