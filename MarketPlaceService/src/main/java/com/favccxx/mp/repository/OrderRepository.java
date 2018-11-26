package com.favccxx.mp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.favccxx.mp.entity.SmartOrder;

public interface OrderRepository extends JpaRepository<SmartOrder, Long> {

	/**
	 * 根据订单编号查询订单
	 * 
	 * @param orderNo
	 * @return
	 */
	SmartOrder findByOrderNo(String orderNo);

	/**
	 * 待支付订单
	 * 
	 * @param username
	 * @param pageable
	 * @return
	 */
	@Query(value = "from SmartOrder o where o.userName=?1 and o.status='CREATED' and o.payStatus=0 order by o.createTime desc", countQuery = " select count(o) FROM SmartOrder o where o.userName=?1 and o.status='CREATED' and o.payStatus=0")
	Page<SmartOrder> needPayQuery(String username, Pageable pageable);

	/**
	 * 已取消订单
	 * 
	 * @param username
	 * @param pageable
	 * @return
	 */
	@Query(value = "from SmartOrder o where o.userName=?1 and o.status='CANCELD' and o.payStatus=0 order by o.createTime desc", countQuery = " select count(o) FROM SmartOrder o where o.userName=?1 and o.status='CANCELD' and o.payStatus=0")
	Page<SmartOrder> canceledQuery(String username, Pageable pageable);

	/**
	 * 已完成订单
	 * 
	 * @param username
	 * @param pageable
	 * @return
	 */
	@Query(value = "from SmartOrder o where o.userName=?1 and o.status='FINISHED' and o.payStatus=1 order by o.createTime desc", countQuery = " select count(o) FROM SmartOrder o where o.userName=?1 and o.status='FINISHED' and o.payStatus=2")
	Page<SmartOrder> finishedQuery(String username, Pageable pageable);

}
