package com.favccxx.mp.entity.req;

/**
 * 订单支付请求对象
 * @author favccxx
 *
 */
public class OrderPayReq {
	
	/**
	 * 订单编号
	 */
	private String orderNo;
	
	/**
	 * 支付类型
	 */
	private String payType;
	
	/**
	 * 提交用户
	 */
	private String username;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
