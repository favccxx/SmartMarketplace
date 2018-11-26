package com.favccxx.mp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.mp.constants.SysConstants.OrderStatus;
import com.favccxx.mp.entity.SmartOrder;
import com.favccxx.mp.entity.SmartOrderPay;
import com.favccxx.mp.entity.SmartOrderProduct;
import com.favccxx.mp.entity.resp.OrderResponse;
import com.favccxx.mp.repository.OrderPayRepository;
import com.favccxx.mp.repository.OrderProductRepository;
import com.favccxx.mp.repository.OrderRepository;
import com.favccxx.mp.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderProductRepository orderProductRepository;
	@Autowired
	OrderPayRepository orderPayRepository;

	@Override
	public SmartOrder findById(long id) {
		return orderRepository.findById(id).get();
	}
	
	@Override
	public OrderResponse findOrderDetail(long id) {
		OrderResponse response = new OrderResponse();
		SmartOrder order = orderRepository.findById(id).get();
		if(order != null) {
			BeanUtils.copyProperties(order, response);
			
			List<SmartOrderProduct>  list = orderProductRepository.findByOrderId(order.getId());
			response.setProductList(list);
		}
		return response;
	}

	@Override
	public SmartOrder finishOrder(SmartOrder order) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public Page<SmartOrder> pageIOrder(String username, String orderStatus, Pageable pageable) {
		Page<SmartOrder> page = null;
		
		if("ALL".equals(orderStatus)) {
			
		}else if("NEEDPAY".equals(orderStatus)) {			
			page = orderRepository.needPayQuery(username, pageable);
		}else if("FINISHED".equals(orderStatus)) {
			page = orderRepository.finishedQuery(username, pageable);
		}else if("CANCELD".equals(orderStatus)) {
			page = orderRepository.canceledQuery(username, pageable);
		}
		return page;
	}


	@Override
	public Page<SmartOrder> pageQuery(SmartOrder order, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public SmartOrder placeOrder(SmartOrder order, List<SmartOrderProduct> opList) {
		orderRepository.save(order);
		for(SmartOrderProduct op : opList) {
			op.setOrderId(order.getId());
			op.setOrderNo(order.getOrderNo());
			orderProductRepository.save(op);
		}
		return order;
	}

	

	@Override
	public void cancelOrder(long id) {
		SmartOrder order = findById(id);
		order.setStatus(OrderStatus.CANCELD);
		order.setUpdateTime(new Date());
		orderRepository.save(order);
	}

	@Transactional
	@Override
	public void deleteOrder(long id) {
		SmartOrder order = findById(id);
		order.setStatus(OrderStatus.DELETED);
		orderRepository.save(order);
	}

	@Transactional
	@Override
	public SmartOrder payOrder(String orderNo, String payType) {
		SmartOrder order = orderRepository.findByOrderNo(orderNo);
		if(order != null) {
			order.setPayStatus(1);
			order.setStatus(OrderStatus.FINISHED);
			orderRepository.save(order);
			
			SmartOrderPay orderPay = new SmartOrderPay();
			orderPay.setOrderId(order.getId());
			orderPay.setOrderNo(order.getOrderNo());
			orderPay.setPayType(payType);
			orderPay.setPrice(order.getOrderPrice());
			orderPay.setPayDate(new Date());
			orderPayRepository.save(orderPay);
		}
		return order;
	}

	@Override
	public SmartOrder findByOrderNo(String orderNo) {
		return orderRepository.findByOrderNo(orderNo);
	}

	

	


	

}
