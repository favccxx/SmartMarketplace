package com.favccxx.mp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.mp.entity.SmartOrder;
import com.favccxx.mp.entity.SmartOrderProduct;
import com.favccxx.mp.entity.resp.OrderResponse;

public interface OrderService {
	
	/**
	 * 根据订单Id查询订单详情
	 * @param id
	 * @return
	 */
	SmartOrder findById(long id);
	
	/**
	 * 根据订单编号查询订单信息
	 * @param orderNo
	 * @return
	 */
	SmartOrder findByOrderNo(String orderNo);
	
	/**
	 * 根据订单Id查询订单详情
	 * @param id
	 * @return
	 */
	OrderResponse findOrderDetail(long id);
	
	/**
	 * 分页查询订单信息
	 * @param order
	 * @param pageable
	 * @return
	 */
	Page<SmartOrder> pageQuery(SmartOrder order, Pageable pageable);
	
	
	/**
	 * 分页查询用户的订单
	 * @param username 用户名
	 * @param orderStatus 
	 * 	ALL 全部订单
	 *  NEEDPAY 待支付
	 *  FINISHED 已完成
	 *  CANCELD 已取消
	 * @param pageable
	 * @return
	 */
	Page<SmartOrder> pageIOrder(String username, String orderStatus, Pageable pageable);

	
	/**
	 * 创建订单
	 * @param order 订单对象
	 * @param opList 产品列表
	 * @return
	 */
	SmartOrder placeOrder(SmartOrder order, List<SmartOrderProduct> opList);
	
	/**
	 * 订单支付接口
	 * @param orderNo 订单编号
	 * @param payType 支付方式
	 * @return
	 */
	SmartOrder payOrder(String orderNo, String payType);
	
	/**
	 * 完成订单
	 * @param order
	 * @return
	 */
	SmartOrder finishOrder(SmartOrder order);
	
	/**
	 * 取消订单
	 * @param id 订单Id
	 */
	void cancelOrder(long id);
	
	
	/**
	 * 删除订单
	 * @param id
	 */
	void deleteOrder(long id);
	
	

}
