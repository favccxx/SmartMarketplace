package com.favccxx.mp.entity.resp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.favccxx.mp.constants.SysConstants.OrderStatus;
import com.favccxx.mp.entity.SmartOrderProduct;

/**
 * 订单查询响应对象
 * 
 * @author favccxx
 *
 */
public class OrderResponse {

	/**
	 * 订单Id
	 */
	private long id;

	/**
	 * 订单编号
	 */
	private String orderNo;

	/**
	 * 会员Id
	 */
	private long vipId;
	
	/**
	 * 会员名称
	 */
	private String vipName;

	/**
	 * 原始金额
	 */
	private BigDecimal originalPrice;

	/**
	 * 订单金额
	 */
	private BigDecimal orderPrice;

	/**
	 * 优惠金额
	 */
	private BigDecimal couponPrice;

	private Date orderDate;

	/**
	 * 订单状态
	 */
	private OrderStatus status;
	
	/**
	 * 支付状态
	 */
	private int payStatus;

	/**
	 * 员工用户名
	 */
	private String userName;

	private Date createTime;

	private Date updateTime;

	private List<SmartOrderProduct> productList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public long getVipId() {
		return vipId;
	}

	public void setVipId(long vipId) {
		this.vipId = vipId;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public BigDecimal getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(BigDecimal couponPrice) {
		this.couponPrice = couponPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<SmartOrderProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<SmartOrderProduct> productList) {
		this.productList = productList;
	}

	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

}
